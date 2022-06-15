package ru.ahoy.customweather.di.modules

import org.koin.dsl.module
import ru.ahoy.customweather.Constants
import ru.ahoy.data.network.di.NetworkModule

val dataModule = module {
    single { NetworkModule.provideRetrofit(okHttpClient = get(), baseUrl = Constants.WEATHER_API_HOST) }

    factory { NetworkModule.provideOkHttpClient(apiKey = Constants.WEATHER_API_KEY) }
    factory { NetworkModule.provideWeatherService(retrofit = get()) }
}