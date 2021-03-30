package com.alvayonara.openweatherapps.core.domain.repository

import com.alvayonara.openweatherapps.core.data.source.Resource
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    fun getWeather(weather: Weather): Flow<Resource<DataEntity>>
}