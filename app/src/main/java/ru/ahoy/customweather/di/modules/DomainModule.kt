package ru.ahoy.customweather.di.modules

import org.koin.dsl.module
import ru.ahoy.domain.usecase.GetWeatherByLatLong

val domainModule = module {
    factory { GetWeatherByLatLong(weatherRepository = get()) }
}