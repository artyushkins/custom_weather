package ru.ahoy.customweather.di.modules

import org.koin.dsl.module
import ru.ahoy.customweather.Constants
import ru.ahoy.data.network.NetworkRepositoryImpl
import ru.ahoy.data.network.di.NetworkModule
import ru.ahoy.domain.repository.WeatherRepository

val dataModule = module {
    single<WeatherRepository> { NetworkRepositoryImpl(service = get()) }
}

val apiModule = module {
    single { NetworkModule.provideRetrofit(okHttpClient = get(), baseUrl = Constants.WEATHER_API_HOST) }

    factory { NetworkModule.provideOkHttpClient(apiKey = Constants.WEATHER_API_KEY) }
    factory { NetworkModule.provideWeatherService(retrofit = get()) }
}