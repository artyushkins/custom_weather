package ru.ahoy.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ahoy.data.network.models.responses.ResponseCurrent

interface WeatherService {

    @GET("/current.json")
    suspend fun getCurrentWeatherByLatLong(@Query("q") latLong: String): ResponseCurrent

    @GET("/current.json")
    suspend fun getCurrentWeatherByCity(@Query("q") city: String): ResponseCurrent

}