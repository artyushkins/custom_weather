package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ahoy.customweather.databinding.FragmentWeatherViewpagerBinding
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState
import ru.ahoy.customweather.presentation.viewmodel.CitiesViewModel
import ru.ahoy.domain.models.Weather
import kotlin.math.abs

class HorizontalViewPagerFragment : BaseFragment() {

    companion object {
        fun newInstance(): HorizontalViewPagerFragment = HorizontalViewPagerFragment()
    }

    override val binding by viewBinding(FragmentWeatherViewpagerBinding::class)
    override val activityState: MainActivityState get() = MainActivityState.MainWeatherScreen
    override val fragment: Fragment get() = this
    private val citiesViewModel by viewModel<CitiesViewModel>()
    private val viewPager: ViewPager2 by lazy { binding.viewPager }
    private var weatherList = listOf<Weather?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citiesViewModel.getCities()
        lifecycleScope.launchWhenResumed {
            citiesViewModel.cities.collect { cities ->
                initViewPager(cities)
            }
        }
    }

    private fun initViewPager(cities: List<Weather>) {
        weatherList = cities
        viewPager.adapter = ViewPagerAdapter()
        viewPager.overScrollMode = ViewPager2.OVER_SCROLL_NEVER
        viewPager.setPageTransformer(PageTransformer())
        binding.dotsIndicator.attachTo(viewPager)
    }

    class PageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.alpha = 1 - abs(position)
        }
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(parentFragmentManager, lifecycle) {

        override fun getItemCount(): Int = weatherList.size

        override fun createFragment(position: Int): Fragment {
            return WeatherFragment.newInstance(weatherList[position])
        }
    }
}