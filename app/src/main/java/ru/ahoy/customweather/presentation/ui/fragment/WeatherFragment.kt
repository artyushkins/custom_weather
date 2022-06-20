package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import ru.ahoy.customweather.databinding.FragmentWeatherBinding
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.views.WeatherView
import ru.ahoy.domain.models.Weather
import ru.ahoy.domain.models.toJson

class WeatherFragment : BaseFragment() {

    companion object {
        private const val key_weather = "weather"

        fun newInstance(weather: Weather?): WeatherFragment =
            WeatherFragment().apply {
                arguments = bundleOf(key_weather to weather?.toJson())
            }
    }

    private val weather by lazy { Weather.fromJson(arguments?.getString(key_weather)) }
    override val binding by viewBinding(FragmentWeatherBinding::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.region.text = weather.location?.region
        binding.city.text = weather.location?.name
        binding.temp.text = weather.current?.tempC.toString()
        binding.timeText.text = weather.current?.lastUpdated?.split(" ")?.get(1).orEmpty()
        binding.uvText.text = weather.current?.uv.toString()
        binding.humidityText.text = weather.current?.humidity.toString()
        binding.rainText.text = weather.current?.precipMm.toString()
        binding.weather.weather = getWeather(weather.current?.condition?.code)
    }

    private fun getWeather(code: Int?): Int {
        return when (code) {
            1000 -> WeatherView.SUNNY
            1003 -> WeatherView.PARTLY_CLOUDY
            1006, 1009 -> WeatherView.CLOUDY
            else -> -1
        }
    }

}