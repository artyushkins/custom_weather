package ru.ahoy.customweather.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ahoy.customweather.presentation.viewmodel.SearchViewModel
import ru.ahoy.customweather.presentation.viewmodel.WeatherViewModel

private val appModule = module {
    viewModel { WeatherViewModel(getWeatherUseCase = get()) }
    viewModel { SearchViewModel(searchUseCase = get()) }
}

val allModules = listOf(dataModule, domainModule, appModule, apiModule)
