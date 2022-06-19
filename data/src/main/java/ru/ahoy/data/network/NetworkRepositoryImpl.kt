package ru.ahoy.data.network

import ru.ahoy.domain.models.Weather
import ru.ahoy.domain.repository.WeatherRepository

class NetworkRepositoryImpl(private val service: WeatherService) : WeatherRepository {
    override suspend fun getWeather(latLong: String): Weather = service.getWeather(latLong)
}