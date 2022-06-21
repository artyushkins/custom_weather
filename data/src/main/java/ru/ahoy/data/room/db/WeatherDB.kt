package ru.ahoy.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ahoy.data.room.dao.WeatherDao
import ru.ahoy.data.room.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDB : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}