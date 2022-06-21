package ru.ahoy.customweather.presentation.ui.interfaces

interface IWeatherFragment : BaseFragmentInterface {
    val isStandalone: Boolean
    fun addWeather()
    fun removeWeather()
}