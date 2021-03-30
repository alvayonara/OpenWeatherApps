package com.alvayonara.openweatherapps.ui

import com.alvayonara.openweatherapps.core.data.source.Resource
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.domain.model.Weather
import com.alvayonara.openweatherapps.core.domain.usecase.WeatherUseCase
import com.alvayonara.openweatherapps.utils.BaseUnitTest
import com.alvayonara.openweatherapps.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest : BaseUnitTest() {

    private lateinit var weatherViewModel: WeatherViewModel

    private val weatherUseCase: WeatherUseCase = mock()
    private val weathers = mock<DataEntity>()
    private val latitude = "-6.110252694250743"
    private val longitude = "106.14598877728035"
    private val weather = Weather(
        lat = latitude,
        long = longitude,
        isSwipeRefreshed = true,
        isNetworkAvailable = true
    )

    private val expected = Resource.Success(weathers)

    @Before
    fun setUp() {
        weatherViewModel = WeatherViewModel(weatherUseCase)
        weatherViewModel.setWeather(weather)
    }

    @Test
    fun `Should get weather from use case`() = runBlockingTest {
        mockSuccessfulCase()
        weatherViewModel.weather().getValueForTest()
        verify(weatherUseCase, times(1)).getWeather(weather)
    }

    @Test
    fun `Should successfully to get weather`() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(expected, weatherViewModel.weather().getValueForTest())
    }

    private fun mockSuccessfulCase() {
        runBlocking {
            whenever(weatherUseCase.getWeather(weather)).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
    }
}