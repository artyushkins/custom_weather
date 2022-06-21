package ru.ahoy.customweather.presentation.ui.fragment

import androidx.fragment.app.Fragment
import ru.ahoy.customweather.databinding.FragmentCitiesBinding
import ru.ahoy.customweather.extension.viewBinding

class CitiesFragment : BaseFragment() {
    override val binding by viewBinding(FragmentCitiesBinding::class)
    override val fragment: Fragment get() = this
}