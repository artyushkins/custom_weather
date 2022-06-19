package ru.ahoy.customweather.extension

import androidx.appcompat.app.AppCompatActivity
import ru.ahoy.customweather.presentation.ui.interfaces.BaseFragmentInterface

inline fun <reified I : BaseFragmentInterface> AppCompatActivity.findFragment(): I? {
    return supportFragmentManager.fragments.filterIsInstance<I>().takeIf { it.isNotEmpty() }?.first()
}