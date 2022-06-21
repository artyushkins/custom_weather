package ru.ahoy.domain.repository

import ru.ahoy.domain.models.SearchItem
import ru.ahoy.domain.models.Weather

interface WeatherRepository {
    suspend fun getWeather(query: String): Weather
    suspend fun search(query: String): List<SearchItem>
    suspend fun removeCity(name: String)
    suspend fun saveCity(name: String)
    suspend fun getCities(): List<String>
    suspend fun isCityInStorage(name: String): Boolean
}