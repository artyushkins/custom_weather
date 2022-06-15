package ru.ahoy.domain.usecase

import ru.ahoy.domain.repository.WeatherRepository

class GetWeatherByLatLong(private val weatherRepository: WeatherRepository) {
    suspend fun execute(latLong: String) = weatherRepository.getWeatherByLatLong(latLong)
}