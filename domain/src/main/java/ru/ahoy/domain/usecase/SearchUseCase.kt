package ru.ahoy.domain.usecase

import ru.ahoy.domain.repository.WeatherRepository

class SearchUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(query: String) = weatherRepository.search(query)
}