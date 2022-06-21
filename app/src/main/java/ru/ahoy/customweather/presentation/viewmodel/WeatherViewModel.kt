package ru.ahoy.customweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.java.KoinJavaComponent
import ru.ahoy.domain.models.Weather
import ru.ahoy.domain.usecase.GetWeatherUseCase
import ru.ahoy.domain.usecase.IsCityInStorageUseCase
import ru.ahoy.domain.usecase.RemoveCityUseCase
import ru.ahoy.domain.usecase.SaveCityUseCase

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveWeatherUseCase: SaveCityUseCase,
    private val removeWeatherUseCase: RemoveCityUseCase,
    private val isCityInStorageUseCase: IsCityInStorageUseCase
) : ViewModel() {

    private val _weather: MutableStateFlow<Weather?> = MutableStateFlow(null)
    val weather = _weather.asStateFlow()

    suspend fun checkCityInStorage(name: String?): Boolean = withContext(Dispatchers.IO) {
        isCityInStorageUseCase.execute(name ?: return@withContext false)
    }

    suspend fun getWeatherByIP() = withContext(Dispatchers.IO) {
        _weather.value = getWeatherUseCase.execute(getNetworkIPAddress())
    }

    fun getWeatherByName(name: String?) {
        viewModelScope.launch {
            _weather.value = getWeatherUseCase.execute(name.orEmpty())
        }
    }

    suspend fun saveWeather(name: String) = withContext(Dispatchers.IO) {
        saveWeatherUseCase.execute(name)
    }

    suspend fun removeWeather(name: String) = withContext(Dispatchers.IO) {
        removeWeatherUseCase.execute(name)
    }

    private fun getNetworkIPAddress(): String {
        val client = KoinJavaComponent.get<OkHttpClient>(OkHttpClient::class.java)
        val request = Request.Builder().url("https://api.ipify.org?format=jsonz").build()
        val response = client.newCall(request).execute()
        return response.body?.string().orEmpty()
    }

}