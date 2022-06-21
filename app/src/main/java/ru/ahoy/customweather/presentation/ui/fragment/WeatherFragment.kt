package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ahoy.customweather.databinding.FragmentWeatherBinding
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState
import ru.ahoy.customweather.presentation.ui.interfaces.IWeatherFragment
import ru.ahoy.customweather.presentation.ui.views.WeatherView
import ru.ahoy.customweather.presentation.viewmodel.WeatherViewModel
import ru.ahoy.domain.models.Weather
import ru.ahoy.domain.models.toJson

class WeatherFragment : BaseFragment(), IWeatherFragment {

    companion object {
        private const val key_weather = "weather"
        private const val key_name = "name"

        fun newInstance(weather: Weather?, id: String? = null): WeatherFragment =
            WeatherFragment().apply {
                arguments = bundleOf(key_weather to weather?.toJson(), key_name to id)
            }
    }

    override val binding by viewBinding(FragmentWeatherBinding::class)
    override val isStandalone: Boolean get() = arguments?.getString(key_name) != null
    override val fragment: Fragment get() = this
    override val activityState: MainActivityState
        get() = if (isStandalone) MainActivityState.DetailScreen(this) else MainActivityState.MainWeatherScreen(this)

    private val weather by lazy { Weather.fromJson(arguments?.getString(key_weather) ?: return@lazy null) }
    private val viewModel by viewModel<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weather?.let { weather ->
            initWeather(weather)
        } ?: run {
            viewModel.getWeatherByName(arguments?.getString(key_name))
            lifecycleScope.launchWhenCreated {
                viewModel.weather.collect { weather ->
                    weather?.let(this@WeatherFragment::initWeather)
                }
            }
        }
    }

    private fun initWeather(weather: Weather) {
        binding.region.text = weather.location?.region
        binding.city.text = weather.location?.name
        binding.tempLayout.temp.text = weather.current?.tempC.toString()
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