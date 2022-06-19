package ru.ahoy.customweather.di.modules

import org.koin.dsl.module
import ru.ahoy.domain.usecase.GetWeatherUseCase

val domainModule = module {
    factory { GetWeatherUseCase(weatherRepository = get()) }
}