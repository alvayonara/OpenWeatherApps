package com.alvayonara.openweatherapps.di

import com.alvayonara.openweatherapps.core.domain.usecase.WeatherInteractor
import com.alvayonara.openweatherapps.core.domain.usecase.WeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideWeatherUseCase(weatherInteractor: WeatherInteractor): WeatherUseCase
}