package com.alvayonara.openweatherapps.core.data.source.remote.network

import com.alvayonara.openweatherapps.core.data.source.remote.response.DataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") long: String,
        @Query("appid") appId: String
    ): DataResponse
}