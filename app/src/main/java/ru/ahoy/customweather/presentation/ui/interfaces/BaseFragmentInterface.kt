package ru.ahoy.customweather.presentation.ui.interfaces

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver

interface BaseFragmentInterface {
    val fragment: Fragment
    var lifecycleObserver: LifecycleObserver?
}