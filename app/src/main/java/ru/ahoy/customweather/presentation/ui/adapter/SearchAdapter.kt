package ru.ahoy.customweather.presentation.ui.adapter

import android.view.ViewGroup
import ru.ahoy.customweather.R
import ru.ahoy.customweather.presentation.ui.adapter.holders.BaseViewHolder
import ru.ahoy.customweather.presentation.ui.adapter.holders.SearchCityViewHolder
import ru.ahoy.domain.models.SearchItem

class SearchAdapter(private val isRecent: Boolean, private val onClick: (String) -> Unit) : BaseAdapter<SearchItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchItem> {
        return SearchCityViewHolder(parent.inflate(R.layout.list_item_search_city), onClick)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SearchItem>, position: Int) {
        if (holder is SearchCityViewHolder) {
            holder.bind(items[position], position == itemCount - 1)
        }
    }

}