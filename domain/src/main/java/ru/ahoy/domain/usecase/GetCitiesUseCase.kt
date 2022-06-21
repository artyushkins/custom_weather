package ru.ahoy.domain.usecase

import ru.ahoy.domain.repository.WeatherRepository

class GetCitiesUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(): List<String> = weatherRepository.getCities()
}