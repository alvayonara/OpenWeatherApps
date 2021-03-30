package com.alvayonara.openweatherapps.core.domain.usecase

import com.alvayonara.openweatherapps.core.data.source.Resource
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.domain.model.Weather
import com.alvayonara.openweatherapps.core.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherInteractor @Inject constructor(private val weatherRepository: IWeatherRepository) :
    WeatherUseCase {

    override fun getWeather(weather: Weather): Flow<Resource<DataEntity>> =
        weatherRepository.getWeather(weather)
}