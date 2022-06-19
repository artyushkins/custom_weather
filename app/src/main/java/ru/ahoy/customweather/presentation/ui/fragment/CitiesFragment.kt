package ru.ahoy.customweather.presentation.ui.fragment

import ru.ahoy.customweather.databinding.FragmentCitiesBinding
import ru.ahoy.customweather.extension.viewBinding

class CitiesFragment : BaseFragment() {
    override val binding by viewBinding(FragmentCitiesBinding::class)
}