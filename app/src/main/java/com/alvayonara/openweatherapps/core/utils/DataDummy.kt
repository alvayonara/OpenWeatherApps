package com.alvayonara.openweatherapps.core.utils

import com.alvayonara.openweatherapps.core.data.source.local.entity.*

object DataDummy {

    fun generateDummyWeathers(): List<DataEntity> {
        val weathers = ArrayList<DataEntity>()
        weathers.add(
            DataEntity(
                id = 6762681,
                name = "Lontarjiwantaka",
                dt = 1617098544,
                weather = listOf(
                    WeatherEntity(
                        main = "Clouds"
                    )
                ),
                main = MainEntity(
                    temp = 302.3,
                    temp_min = 302.04,
                    temp_max = 303.15,
                    pressure = "1005",
                    humidity = "74"
                ),
                wind = WindEntity(speed = "2.57"),
                sys = SysEntity(
                    country = "ID",
                    sunrise = 1617058684,
                    sunset = 1617102094
                )
            )
        )
        weathers.add(
            DataEntity(
                id = 6762692,
                name = "BSD City",
                dt = 1617098544,
                weather = listOf(
                    WeatherEntity(
                        main = "Rain"
                    )
                ),
                main = MainEntity(
                    temp = 252.5,
                    temp_min = 251.10,
                    temp_max = 253.52,
                    pressure = "976",
                    humidity = "89"
                ),
                wind = WindEntity(speed = "4.52"),
                sys = SysEntity(
                    country = "ID",
                    sunrise = 1617058689,
                    sunset = 1617102099
                )
            )
        )
        return weathers
    }
}