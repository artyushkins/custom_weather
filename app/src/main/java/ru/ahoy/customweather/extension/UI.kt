package ru.ahoy.customweather.extension

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView

val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun View.onClick(listener: () -> Unit) {
    setOnClickListener { listener() }
}

fun SearchView.onFocused(listener: () -> Unit) {
    setOnQueryTextFocusChangeListener { _, focused ->
        if (focused) listener()
    }
}