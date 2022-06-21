package ru.ahoy.customweather.presentation.ui.adapter.holders

import android.view.View
import ru.ahoy.customweather.databinding.ListItemSearchRecentBinding
import ru.ahoy.customweather.extension.layoutInflater
import ru.ahoy.domain.models.SearchItem


class SearchRecentViewHolder(view: View) : BaseViewHolder<SearchItem>(view) {
    private val binding = ListItemSearchRecentBinding.inflate(view.layoutInflater)

    override fun bind(item: SearchItem) {
        binding.text.text = item.name
    }
}