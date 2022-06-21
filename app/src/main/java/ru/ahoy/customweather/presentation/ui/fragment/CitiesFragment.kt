package ru.ahoy.customweather.presentation.ui.fragment

import androidx.fragment.app.Fragment
import ru.ahoy.customweather.databinding.FragmentCitiesBinding
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState

class CitiesFragment : BaseFragment() {
    override val activityState: MainActivityState get() = MainActivityState.CitiesScreen(this)
    override val binding by viewBinding(FragmentCitiesBinding::class)
    override val fragment: Fragment get() = this
}