package ru.ahoy.customweather.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ahoy.customweather.presentation.flow.MainActivityStateFlow
import ru.ahoy.customweather.presentation.viewmodel.CitiesViewModel
import ru.ahoy.customweather.presentation.viewmodel.SearchViewModel
import ru.ahoy.customweather.presentation.viewmodel.WeatherViewModel

private val appModule = module {
    viewModel { SearchViewModel(searchUseCase = get()) }
    viewModel { CitiesViewModel(getCitiesUseCase = get(), getWeatherUseCase = get()) }
    viewModel {
        WeatherViewModel(
            getWeatherUseCase = get(),
            saveWeatherUseCase = get(),
            removeWeatherUseCase = get(),
            isCityInStorageUseCase = get()
        )
    }

    single { MainActivityStateFlow() }
}

val allModules = listOf(dataModule, domainModule, appModule, apiModule)
