package com.alvayonara.openweatherapps.core.di

import com.alvayonara.openweatherapps.core.data.source.WeatherRepository
import com.alvayonara.openweatherapps.core.domain.repository.IWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(weatherRepository: WeatherRepository): IWeatherRepository
}