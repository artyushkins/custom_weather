package ru.ahoy.customweather.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ahoy.customweather.R
import ru.ahoy.customweather.presentation.ui.fragment.WeatherFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, WeatherFragment()).commit()
    }
}