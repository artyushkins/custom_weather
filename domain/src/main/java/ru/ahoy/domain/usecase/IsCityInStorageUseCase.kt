package ru.ahoy.domain.usecase

import ru.ahoy.domain.repository.WeatherRepository

class IsCityInStorageUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(name: String): Boolean = weatherRepository.isCityInStorage(name)
}