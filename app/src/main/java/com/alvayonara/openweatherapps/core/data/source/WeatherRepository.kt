package com.alvayonara.openweatherapps.core.data.source

import com.alvayonara.openweatherapps.core.data.source.local.LocalDataSource
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.data.source.remote.RemoteDataSource
import com.alvayonara.openweatherapps.core.data.source.remote.network.ApiResponse
import com.alvayonara.openweatherapps.core.data.source.remote.response.DataResponse
import com.alvayonara.openweatherapps.core.domain.repository.IWeatherRepository
import com.alvayonara.openweatherapps.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IWeatherRepository {

    override fun getWeather(
        lat: String,
        long: String,
        isSwipeRefreshed: Boolean,
        isNetworkAvailable: Boolean
    ): Flow<Resource<DataEntity>> =
        object : NetworkBoundResource<DataEntity, DataResponse>() {
            override fun loadFromDB(): Flow<DataEntity> =
                localDataSource.getUpdatedWeather()

            override fun shouldFetch(data: DataEntity?): Boolean =
                data == null || isSwipeRefreshed || isNetworkAvailable

            override suspend fun createCall(): Flow<ApiResponse<DataResponse>> {
                return remoteDataSource.getWeather(lat, long)
            }

            override suspend fun saveCallResult(data: DataResponse) {
                val weather = DataMapper.mapResponseToEntity(data)
                localDataSource.insertWeather(weather)
            }
        }.asFlow()
}