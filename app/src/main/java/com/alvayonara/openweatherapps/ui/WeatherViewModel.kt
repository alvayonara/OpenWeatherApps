package com.alvayonara.openweatherapps.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alvayonara.openweatherapps.core.domain.usecase.WeatherUseCase

class WeatherViewModel @ViewModelInject constructor(private val weatherUseCase: WeatherUseCase) :
    ViewModel() {

    fun weather(lat: String, long: String, isSwipeRefreshed: Boolean, isNetworkAvailable: Boolean) =
        weatherUseCase.getWeather(lat, long, isSwipeRefreshed, isNetworkAvailable).asLiveData()
}