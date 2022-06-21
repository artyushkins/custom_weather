package ru.ahoy.customweather.extension

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import ru.ahoy.customweather.presentation.ui.activity.MainActivity

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

fun MainActivity.getDrawableById(id: Int): Drawable? {
    return AppCompatResources.getDrawable(this, id)
}