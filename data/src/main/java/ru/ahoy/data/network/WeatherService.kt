package ru.ahoy.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ahoy.domain.models.Weather

interface WeatherService {

    @GET("v1/current.json")
    suspend fun getWeather(@Query("q") latLong: String): Weather

    @GET("v1/current.json")
    suspend fun getCurrentWeatherByCity(@Query("q") city: String): Weather

}