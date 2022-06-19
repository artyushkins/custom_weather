package ru.ahoy.customweather.presentation.ui.fragment

import ru.ahoy.customweather.databinding.FragmentWeatherBinding
import ru.ahoy.customweather.extension.viewBinding

class WeatherFragment : BaseFragment() {
    override val binding by viewBinding(FragmentWeatherBinding::class)
}