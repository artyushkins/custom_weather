package ru.ahoy.customweather.di.modules

import org.koin.dsl.module
import ru.ahoy.domain.usecase.*

val domainModule = module {
    factory { GetWeatherUseCase(weatherRepository = get()) }
    factory { SearchUseCase(weatherRepository = get()) }
    factory { SaveCityUseCase(weatherRepository = get()) }
    factory { RemoveCityUseCase(weatherRepository = get()) }
    factory { GetCitiesUseCase(weatherRepository = get()) }
    factory { IsCityInStorageUseCase(weatherRepository = get()) }
}