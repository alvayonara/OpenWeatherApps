package com.alvayonara.openweatherapps.core.data.source.remote

import android.annotation.SuppressLint
import com.alvayonara.openweatherapps.BuildConfig
import com.alvayonara.openweatherapps.core.data.source.remote.network.ApiResponse
import com.alvayonara.openweatherapps.core.data.source.remote.network.ApiService
import com.alvayonara.openweatherapps.core.data.source.remote.response.DataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    @SuppressLint("CheckResult")
    suspend fun getWeather(lat: String, long: String): Flow<ApiResponse<DataResponse>> = flow {
        try {
            val response = apiService.getWeather(lat, long, BuildConfig.API_KEY)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Timber.e(e.toString())
        }
    }.flowOn(Dispatchers.IO)
}