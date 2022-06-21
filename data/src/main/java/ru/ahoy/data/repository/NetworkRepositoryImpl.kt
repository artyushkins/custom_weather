package ru.ahoy.data.repository

import ru.ahoy.data.network.WeatherService
import ru.ahoy.domain.models.SearchItem
import ru.ahoy.domain.models.Weather
import ru.ahoy.domain.repository.WeatherRepository

class NetworkRepositoryImpl(private val service: WeatherService) : WeatherRepository {
    override suspend fun getWeather(query: String): Weather = service.getWeather(query)

    override suspend fun search(query: String): List<SearchItem> = service.search(query)
}