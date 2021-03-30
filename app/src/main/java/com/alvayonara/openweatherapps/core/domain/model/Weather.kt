package com.alvayonara.openweatherapps.core.domain.model

data class Weather(
    val lat: String,
    val long: String,
    val isSwipeRefreshed: Boolean,
    val isNetworkAvailable: Boolean
)