package ru.ahoy.customweather.presentation.ui.adapter.holders

import android.view.View
import ru.ahoy.customweather.databinding.ListItemCityBinding
import ru.ahoy.customweather.extension.onClick
import ru.ahoy.customweather.presentation.ui.views.WeatherView
import ru.ahoy.domain.models.Weather

class CityViewHolder(view: View, private val onClick: (String) -> Unit) : BaseViewHolder<Weather>(view) {
    private val binding = ListItemCityBinding.bind(view)

    override fun bind(item: Weather) {
        binding.city.text = item.location?.name
        binding.tempLayout.temp.text = item.current?.tempC.toString()
        binding.weatherView.weather = WeatherView.getWeather(item.current?.condition?.code)
        binding.root.onClick { onClick.invoke(item.location?.name.orEmpty()) }
    }
}