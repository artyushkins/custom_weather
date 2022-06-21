package ru.ahoy.customweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.ahoy.domain.models.Weather
import ru.ahoy.domain.usecase.GetCitiesUseCase
import ru.ahoy.domain.usecase.GetWeatherUseCase

class CitiesViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _cities: MutableStateFlow<List<Weather>> = MutableStateFlow(listOf())
    val cities: StateFlow<List<Weather>> = _cities.asStateFlow()

    fun getCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cities = getCitiesUseCase.execute()
                val deferred = cities.map { city ->
                    async {
                        getWeatherUseCase.execute(city)
                    }
                }
                _cities.value = deferred.awaitAll()
            }
        }
    }
}