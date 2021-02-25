package com.alvayonara.openweatherapps

import android.Manifest
import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.alvayonara.openweatherapps.core.data.source.Resource
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.utils.DateConverter.Companion.FORMAT_DATE_TIME_WITH_AP_PM
import com.alvayonara.openweatherapps.core.utils.DateConverter.Companion.FORMAT_ONLY_TIME
import com.alvayonara.openweatherapps.core.utils.Helper.convertToCelsius
import com.alvayonara.openweatherapps.core.utils.Helper.dateTimeConvert
import com.alvayonara.openweatherapps.core.utils.gone
import com.alvayonara.openweatherapps.core.utils.snack
import com.alvayonara.openweatherapps.core.utils.visible
import com.alvayonara.openweatherapps.databinding.ActivityMainBinding
import com.alvayonara.openweatherapps.ui.BaseActivity
import com.alvayonara.openweatherapps.ui.WeatherViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        initView()
        initLocation()
    }

    private fun initView() {
        binding.swipeRefresh.setOnRefreshListener { initLocation() }
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
        // The request may take little longer because it uses PRIORITY_HIGH_ACCURACY.
        val mLocationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    binding.progressBar.gone()
                    binding.constraintLayoutParent.snack("We cant find your Location. Please try again later")
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        subscribeViewModel(location.latitude, location.longitude)
                    } else {
                        subscribeViewModel(0.0, 0.0)
                    }
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    private fun subscribeViewModel(lat: Double, long: Double) {
        weatherViewModel.weather(
            lat = lat.toString(),
            long = long.toString(),
            isSwipeRefreshed = binding.swipeRefresh.isRefreshing,
            isNetworkAvailable = isNetworkAvailable()
        )
            .observe(this, {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> binding.progressBar.visible()
                        is Resource.Success -> {
                            binding.progressBar.gone()
                            binding.swipeRefresh.isRefreshing = false
                            populateWeatherData(it.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.gone()
                            binding.swipeRefresh.isRefreshing = false
                            binding.constraintLayoutParent.snack("Please check your internet connection or try again later")
                        }
                    }
                }
            })
    }

    private fun populateWeatherData(data: DataEntity?) {
        data?.let {
            with(binding) {
                tvCurrentLocation.text = "${it.name}, ${it.sys?.country}"
                tvLastUpdated.text =
                    "Updated at: ${it.dt.dateTimeConvert(FORMAT_DATE_TIME_WITH_AP_PM)}"
                tvWeather.text = it.weather?.get(0)?.main
                tvTemperature.text = "${it.main?.temp.convertToCelsius()}°C"
                tvMinTemp.text = "Min temp: ${it.main?.temp_min.convertToCelsius()}°C"
                tvMaxTemp.text = "Max temp: ${it.main?.temp_max.convertToCelsius()}°C"
                tvSunrise.text = it.sys?.sunrise.dateTimeConvert(FORMAT_ONLY_TIME)
                tvSunset.text = it.sys?.sunset.dateTimeConvert(FORMAT_ONLY_TIME)
                tvPressure.text = it.main?.pressure
                tvWind.text = it.wind?.speed
                tvHumidity.text = it.main?.humidity
            }
        }
    }
}