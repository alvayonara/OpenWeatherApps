package com.alvayonara.openweatherapps.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.*
import com.alvayonara.openweatherapps.core.utils.RoomConverter

@Entity(tableName = "data")
data class DataEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: Int? = 0,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "dt") var dt: Long? = 0,
    @TypeConverters(RoomConverter::class) var weather: List<WeatherEntity>? = null,
    @Embedded var main: MainEntity? = null,
    @Embedded var wind: WindEntity? = null,
    @Embedded var sys: SysEntity? = null
)

data class WeatherEntity(
    @ColumnInfo(name = "main") var main: String? = ""
)

data class MainEntity(
    @ColumnInfo(name = "temp") var temp: Double? = 0.0,
    @ColumnInfo(name = "temp_min") var temp_min: Double? = 0.0,
    @ColumnInfo(name = "temp_max") var temp_max: Double? = 0.0,
    @ColumnInfo(name = "pressure") var pressure: String? = "",
    @ColumnInfo(name = "humidity") var humidity: String? = ""
)

data class WindEntity(
    @ColumnInfo(name = "speed") var speed: String? = ""
)

data class SysEntity(
    @ColumnInfo(name = "country") var country: String? = "",
    @ColumnInfo(name = "sunrise") var sunrise: Long? = 0,
    @ColumnInfo(name = "sunset") var sunset: Long? = 0
)
