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

class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val _weather: MutableStateFlow<Weather?> = MutableStateFlow(null)
    val weather = _weather.asStateFlow()

    suspend fun getWeatherByIP() = withContext(Dispatchers.IO) {
        _weather.value = getWeatherUseCase.execute(getNetworkIPAddress())
    }

    fun getWeatherByName(name: String?) {
        viewModelScope.launch {
            _weather.value = getWeatherUseCase.execute(name.orEmpty())
        }
    }

    private fun getNetworkIPAddress(): String {
        val client = KoinJavaComponent.get<OkHttpClient>(OkHttpClient::class.java)
        val request = Request.Builder().url("https://api.ipify.org?format=jsonz").build()
        val response = client.newCall(request).execute()
        return response.body?.string().orEmpty()
    }

}