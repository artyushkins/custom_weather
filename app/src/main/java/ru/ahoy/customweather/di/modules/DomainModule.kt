package ru.ahoy.customweather.di.modules

import org.koin.dsl.module
import ru.ahoy.domain.usecase.GetWeatherUseCase
import ru.ahoy.domain.usecase.SearchUseCase

val domainModule = module {
    factory { GetWeatherUseCase(weatherRepository = get()) }
    factory { SearchUseCase(weatherRepository = get()) }
}