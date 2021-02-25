package com.alvayonara.openweatherapps.core.utils

import com.alvayonara.openweatherapps.core.data.source.local.entity.*
import com.alvayonara.openweatherapps.core.data.source.remote.response.DataResponse

object DataMapper {

    fun mapResponseToEntity(input: DataResponse): DataEntity {
        return DataEntity(
            id = input.id,
            name = input.name,
            dt = input.dt,
            weather = input.weather?.map { data ->
                WeatherEntity(
                    main = data.main
                )
            },
            main = input.main?.let { data ->
                MainEntity(
                    temp = data.temp,
                    temp_min = data.temp_min,
                    temp_max = data.temp_max,
                    pressure = data.pressure,
                    humidity = data.humidity
                )
            },
            wind = WindEntity(speed = input.wind?.speed),
            sys = input.sys?.let { data ->
                SysEntity(
                    country = data.country,
                    sunrise = data.sunrise,
                    sunset = data.sunset
                )
            }
        )
    }
}