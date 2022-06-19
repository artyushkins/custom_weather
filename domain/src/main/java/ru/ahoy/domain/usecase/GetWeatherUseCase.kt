package ru.ahoy.domain.usecase

import ru.ahoy.domain.repository.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(query: String) = weatherRepository.getWeather(query)
}