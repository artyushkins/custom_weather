package ru.ahoy.domain.repository

import ru.ahoy.domain.models.SearchItem
import ru.ahoy.domain.models.Weather

interface WeatherRepository {
    suspend fun getWeather(latLong: String): Weather
    suspend fun search(query: String): List<SearchItem>
}