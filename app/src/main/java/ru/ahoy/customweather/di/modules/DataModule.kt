package ru.ahoy.customweather.di.modules

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.ahoy.customweather.Constants
import ru.ahoy.data.network.di.NetworkModule
import ru.ahoy.data.repository.WeatherRepositoryImpl
import ru.ahoy.data.room.di.RoomModule
import ru.ahoy.domain.repository.WeatherRepository

val dataModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(service = get(), weatherDao = get()) }
}

val apiModule = module {
    single { NetworkModule.provideRetrofit(okHttpClient = get(), baseUrl = Constants.WEATHER_API_HOST) }
    single { RoomModule.provideWeatherDB(context = androidContext()) }
    single { RoomModule.provideWeatherDao(weatherDB = get()) }

    factory { NetworkModule.provideOkHttpClient(apiKey = Constants.WEATHER_API_KEY) }
    factory { NetworkModule.provideWeatherService(retrofit = get()) }
}