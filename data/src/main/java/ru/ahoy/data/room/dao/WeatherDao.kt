package ru.ahoy.data.room.dao

import androidx.room.*
import ru.ahoy.data.room.entity.WeatherEntity


@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(weatherEntity: WeatherEntity)

    @Delete
    fun delete(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weatherentity")
    fun getAllCities(): List<WeatherEntity>

    @Query("SELECT * FROM weatherentity WHERE name = :name")
    fun getCity(name: String): WeatherEntity?
}