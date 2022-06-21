package ru.ahoy.data.room.di

import android.content.Context
import androidx.room.Room
import ru.ahoy.data.room.dao.WeatherDao
import ru.ahoy.data.room.db.WeatherDB

object RoomModule {

    private const val weather_database = "weather_database"

    fun provideWeatherDB(context: Context): WeatherDB {
        return Room.databaseBuilder(context, WeatherDB::class.java, weather_database).build()
    }

    fun provideWeatherDao(weatherDB: WeatherDB): WeatherDao {
        return weatherDB.weatherDao
    }
}