package ru.ahoy.domain.usecase

import ru.ahoy.domain.repository.WeatherRepository

class RemoveCityUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(name: String) = weatherRepository.removeCity(name)
}