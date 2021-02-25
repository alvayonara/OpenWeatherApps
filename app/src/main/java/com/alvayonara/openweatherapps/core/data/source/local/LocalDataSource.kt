package com.alvayonara.openweatherapps.core.data.source.local

import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.data.source.local.room.WeatherDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val weatherDao: WeatherDao) {

    fun getUpdatedWeather(): Flow<DataEntity> = weatherDao.getUpdatedWeather()

    suspend fun insertWeather(weather: DataEntity) = weatherDao.insertWeather(weather)
}