package ru.ahoy.domain.usecase

import ru.ahoy.domain.repository.WeatherRepository

class SaveCityUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(name: String) = weatherRepository.saveCity(name)
}