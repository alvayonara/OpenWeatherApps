package com.alvayonara.openweatherapps.core.utils

import com.alvayonara.openweatherapps.core.domain.model.Weather

object DataDummy {

    fun dummyWeather(): Weather = Weather(
        lat = "-6.110252694250743",
        long = "106.14598877728035",
        isSwipeRefreshed = true,
        isNetworkAvailable = true
    )
}