package ru.ahoy.customweather.presentation.ui.adapter

import android.view.ViewGroup
import ru.ahoy.customweather.R
import ru.ahoy.customweather.presentation.ui.adapter.holders.BaseViewHolder
import ru.ahoy.customweather.presentation.ui.adapter.holders.CityViewHolder
import ru.ahoy.domain.models.Weather

class CitiesAdapter(private val onClick: (String) -> Unit) : BaseAdapter<Weather>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Weather> {
        return CityViewHolder(parent.inflate(R.layout.list_item_city), onClick)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Weather>, position: Int) {
        if (holder is CityViewHolder) {
            holder.bind(items[position])
        }
    }

}