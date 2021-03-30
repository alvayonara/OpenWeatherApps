package com.alvayonara.openweatherapps.ui

import android.Manifest
import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alvayonara.openweatherapps.core.base.BaseActivity
import com.alvayonara.openweatherapps.core.data.source.Resource
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.domain.model.Weather
import com.alvayonara.openweatherapps.core.utils.*
import com.alvayonara.openweatherapps.core.utils.Helper.convertToCelsius
import com.alvayonara.openweatherapps.core.utils.Helper.dateTimeConvert
import com.alvayonara.openweatherapps.databinding.ActivityWeatherBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : BaseActivity<ActivityWeatherBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val weatherViewModel: WeatherViewModel by viewModels()

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override val bindingInflater: (LayoutInflater) -> ActivityWeatherBinding
        get() = ActivityWeatherBinding::inflate

    override fun setup() {
        initView()
        initLocation()
        subscribeViewModel()
    }

    private fun initView() {
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    private fun initLocation() {
        requestPermissions(listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
            action = {
                initCurrentLocation()
            }, actionDeny = { showDenyPermission() })
    }

    /**
     *  Ignore default Permission provided by Android.
     *  Already used Karumi Dexter library for handling permission.
     */
    @SuppressLint("MissingPermission")
    private fun initCurrentLocation() {
        // Using Location Request to get accurate location.
        val mLocationRequest = LocationRequest.create().apply {
            interval = 200
            fastestInterval = 200
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }

        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    location?.let {
                        latitude = it.latitude
                        longitude = it.longitude
                        setWeather(latitude, longitude)
                    }
                }
            }
        }

        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    private fun setWeather(lat: Double, long: Double) {
        weatherViewModel.setWeather(
            weather = Weather(
                lat = lat.toString(),
                long = long.toString(),
                isSwipeRefreshed = binding.swipeRefresh.isRefreshing,
                isNetworkAvailable = isNetworkAvailable()
            )
        )
    }

    override fun subscribeViewModel() {
        weatherViewModel.weather().onLiveDataResult {
            when (it) {
                is Resource.Loading -> binding.progressBar.visible()
                is Resource.Success -> {
                    binding.progressBar.gone()
                    setLog(it.data.toString())
                    populateWeatherData(it.data)
                }
                is Resource.Error -> {
                    binding.progressBar.gone()
                    binding.constraintLayoutParent.snack("Please check your internet connection or try again later")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateWeatherData(data: DataEntity?) {
        data?.let {
            with(binding) {
                tvCurrentLocation.text = "${it.name}, ${it.sys?.country}"
                tvLastUpdated.text =
                    "Updated at: ${it.dt.dateTimeConvert(DateConverter.FORMAT_DATE_TIME_WITH_AP_PM)}"
                tvWeather.text = it.weather?.get(0)?.main
                tvTemperature.text = "${it.main?.temp.convertToCelsius()}°C"
                tvMinTemp.text = "Min temp: ${it.main?.temp_min.convertToCelsius()}°C"
                tvMaxTemp.text = "Max temp: ${it.main?.temp_max.convertToCelsius()}°C"
                tvSunrise.text = it.sys?.sunrise.dateTimeConvert(DateConverter.FORMAT_ONLY_TIME)
                tvSunset.text = it.sys?.sunset.dateTimeConvert(DateConverter.FORMAT_ONLY_TIME)
                tvPressure.text = it.main?.pressure
                tvWind.text = it.wind?.speed
                tvHumidity.text = it.main?.humidity
            }
        }
    }

    override fun onRefresh() {
        setWeather(latitude, longitude)
        binding.swipeRefresh.hideLoading()
    }

    override fun onResume() {
        super.onResume()
        setWeather(latitude, longitude)
    }
}