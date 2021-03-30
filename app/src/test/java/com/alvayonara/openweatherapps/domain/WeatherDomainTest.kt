package com.alvayonara.openweatherapps.domain

import com.alvayonara.openweatherapps.core.domain.model.Weather
import com.alvayonara.openweatherapps.core.domain.repository.IWeatherRepository
import com.alvayonara.openweatherapps.core.domain.usecase.WeatherInteractor
import com.alvayonara.openweatherapps.core.domain.usecase.WeatherUseCase
import com.alvayonara.openweatherapps.core.utils.DataDummy
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test

class WeatherDomainTest {

    private lateinit var weatherUseCase: WeatherUseCase

    private val weatherRepository: IWeatherRepository = mock()

    private val weather = DataDummy.dummyWeather()

    @Before
    fun setUp() {
        weatherUseCase = WeatherInteractor(weatherRepository)
    }

    @Test
    fun `Should get weather from repository`() {
        weatherUseCase.getWeather(weather)
        verify(weatherRepository).getWeather(weather)
        verifyNoMoreInteractions(weatherRepository)
    }
}