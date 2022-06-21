package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import ru.ahoy.customweather.databinding.FragmentCitiesBinding
import ru.ahoy.customweather.extension.showFragment
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState
import ru.ahoy.customweather.presentation.ui.adapter.CitiesAdapter
import ru.ahoy.customweather.presentation.viewmodel.CitiesViewModel

class CitiesFragment : BaseFragment() {

    companion object {
        fun newInstance(): CitiesFragment = CitiesFragment()
    }

    override val activityState: MainActivityState get() = MainActivityState.CitiesScreen
    override val binding by viewBinding(FragmentCitiesBinding::class)
    override val fragment: Fragment get() = this

    private val viewModel by inject<CitiesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CitiesAdapter { name -> showWeatherFragment(name = name) }
        binding.cities.adapter = adapter
        binding.cities.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launchWhenResumed {
            viewModel.getCities()
            viewModel.cities.collect { cities ->
                adapter.items = cities
            }
        }
    }

    private fun showWeatherFragment(name: String) {
        showFragment(WeatherFragment.newInstance(null, name), isAdd = true)
    }
}