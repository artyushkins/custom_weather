package ru.ahoy.customweather.presentation.ui.interfaces

import androidx.appcompat.widget.SearchView

interface IMainActivity {
    fun setQueryTextListener(listener: SearchView.OnQueryTextListener?)
    fun onShowWeatherFromSearch()
}