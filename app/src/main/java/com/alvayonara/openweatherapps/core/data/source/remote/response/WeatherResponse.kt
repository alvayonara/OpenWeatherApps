package com.alvayonara.openweatherapps.core.data.source.remote.response

import com.squareup.moshi.Json

data class DataResponse(
    @field:Json(name = "weather") val weather: List<WeatherResponse>? = null,
    @field:Json(name = "main") val main: MainResponse? = null,
    @field:Json(name = "wind") val wind: WindResponse? = null,
    @field:Json(name = "dt") val dt: Long? = 0,
    @field:Json(name = "sys") val sys: SysResponse? = null,
    @field:Json(name = "id") val id: Int? = 0,
    @field:Json(name = "name") val name: String? = ""
)

data class WeatherResponse(
    @field:Json(name = "main") val main: String? = ""
)

data class MainResponse(
    @field:Json(name = "temp") val temp: Double? = 0.0,
    @field:Json(name = "temp_min") val temp_min: Double? = 0.0,
    @field:Json(name = "temp_max") val temp_max: Double? = 0.0,
    @field:Json(name = "pressure") val pressure: String? = "",
    @field:Json(name = "humidity") val humidity: String? = "",
)

data class WindResponse(
    @field:Json(name = "speed") val speed: String? = ""
)

data class SysResponse(
    @field:Json(name = "country") val country: String? = "",
    @field:Json(name = "sunrise") val sunrise: Long? = 0,
    @field:Json(name = "sunset") val sunset: Long? = 0
)
