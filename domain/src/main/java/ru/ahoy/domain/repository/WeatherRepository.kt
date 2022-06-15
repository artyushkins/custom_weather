package ru.ahoy.domain.repository

interface WeatherRepository {
    fun getCurrentWeatherByLatLong()
}