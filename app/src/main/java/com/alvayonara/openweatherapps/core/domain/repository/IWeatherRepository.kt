package com.alvayonara.openweatherapps.core.domain.repository

import com.alvayonara.openweatherapps.core.data.source.Resource
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    fun getWeather(
        lat: String,
        long: String,
        isSwipeRefreshed: Boolean,
        isNetworkAvailable: Boolean
    ): Flow<Resource<DataEntity>>
}