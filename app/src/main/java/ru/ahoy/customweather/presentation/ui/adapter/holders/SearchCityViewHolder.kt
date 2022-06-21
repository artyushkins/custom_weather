package ru.ahoy.customweather.presentation.ui.adapter.holders

import android.view.View
import androidx.core.view.isVisible
import ru.ahoy.customweather.databinding.ListItemSearchCityBinding
import ru.ahoy.domain.models.SearchItem

class SearchCityViewHolder(view: View, private val onClick: (String) -> Unit) : BaseViewHolder<SearchItem>(view) {
    private val binding = ListItemSearchCityBinding.bind(view)

    fun bind(item: SearchItem, isLast: Boolean) {
        binding.city.text = item.name
        binding.divider.isVisible != isLast
        binding.root.setOnClickListener {
            onClick.invoke(item.name)
        }
    }

    override fun bind(item: SearchItem) {

    }
}