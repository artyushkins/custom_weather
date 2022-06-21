package ru.ahoy.data.repository

import ru.ahoy.data.network.WeatherService
import ru.ahoy.data.room.dao.WeatherDao
import ru.ahoy.data.room.entity.WeatherEntity
import ru.ahoy.domain.models.SearchItem
import ru.ahoy.domain.models.Weather
import ru.ahoy.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val service: WeatherService, private val weatherDao: WeatherDao) :
    WeatherRepository {
    override suspend fun getWeather(query: String): Weather = service.getWeather(query)

    override suspend fun search(query: String): List<SearchItem> = service.search(query)

    override suspend fun saveCity(name: String) {
        val entity = WeatherEntity()
        entity.name = name
        weatherDao.insert(entity)
    }

    override suspend fun removeCity(name: String) {
        val entity = WeatherEntity()
        entity.name = name
        weatherDao.delete(entity)
    }

    override suspend fun getCities(): List<String> {
        return weatherDao.getAllCities().map { it.name }
    }

    override suspend fun isCityInStorage(name: String): Boolean {
        return weatherDao.getCity(name) != null
    }
}