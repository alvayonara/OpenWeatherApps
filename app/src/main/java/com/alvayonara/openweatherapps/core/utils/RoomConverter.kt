package com.alvayonara.openweatherapps.core.utils

import androidx.room.TypeConverter
import com.alvayonara.openweatherapps.core.data.source.local.entity.WeatherEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object RoomConverter {

    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val type = Types.newParameterizedType(List::class.java, WeatherEntity::class.java)
    private val adapter = moshi.adapter<List<WeatherEntity>>(type)

    @TypeConverter
    @JvmStatic
    fun fromStringToWeathers(value: String): List<WeatherEntity> =
        value.let { adapter.fromJson(it).orEmpty() }

    @TypeConverter
    @JvmStatic
    fun fromWeathersToString(weathers: List<WeatherEntity>): String = adapter.toJson(weathers)
}