package com.alvayonara.openweatherapps.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.alvayonara.openweatherapps.core.domain.model.Weather
import com.alvayonara.openweatherapps.core.domain.usecase.WeatherUseCase

class WeatherViewModel @ViewModelInject constructor(private val weatherUseCase: WeatherUseCase) :
    ViewModel() {

    private val weather = MutableLiveData<Weather>()

    fun setWeather(weather: Weather) {
        this.weather.value = weather
    }

    fun weather() = weather.switchMap {
        weatherUseCase.getWeather(it).asLiveData()
    }
}