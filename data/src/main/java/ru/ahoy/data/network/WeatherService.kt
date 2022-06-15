package ru.ahoy.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ahoy.domain.models.Weather

interface WeatherService {

    @GET("/current.json")
    suspend fun getWeatherByLatLong(@Query("q") latLong: String): Weather

    @GET("/current.json")
    suspend fun getCurrentWeatherByCity(@Query("q") city: String): Weather

}