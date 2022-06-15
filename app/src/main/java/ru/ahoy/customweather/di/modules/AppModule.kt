package ru.ahoy.customweather.di.modules

import org.koin.dsl.module

private val appModule = module {

}

val allModules = listOf(dataModule, domainModule, appModule)
