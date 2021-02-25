package com.alvayonara.openweatherapps.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import com.alvayonara.openweatherapps.core.utils.RoomConverter

@Database(
    entities = [DataEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}