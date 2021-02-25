package com.alvayonara.openweatherapps.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alvayonara.openweatherapps.core.data.source.local.entity.DataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM data WHERE id = (SELECT MAX(id) FROM data)")
    fun getUpdatedWeather(): Flow<DataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: DataEntity)
}