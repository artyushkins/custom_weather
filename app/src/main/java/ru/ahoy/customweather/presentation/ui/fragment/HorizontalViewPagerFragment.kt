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
import ru.ahoy.customweather.presentation.viewmodel.WeatherViewModel
import ru.ahoy.domain.models.Weather

class HorizontalViewPagerFragment : BaseFragment() {

    override val binding by viewBinding(FragmentWeatherViewpagerBinding::class)
    override val fragment: Fragment get() = this
    private val viewModel by viewModel<WeatherViewModel>()
    private val viewPager: ViewPager2 by lazy { binding.viewPager }
    private var weatherList = listOf<Weather?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.getWeatherByIP()
            viewModel.weather.collect {
                weatherList = listOf(it)
                initViewPager()
            }
        }
    }

    private fun initViewPager() {
        viewPager.adapter = ViewPagerAdapter()
        viewPager.overScrollMode = ViewPager2.OVER_SCROLL_NEVER
        viewPager.setCurrentItem(VerticalFragment.Weather.position, false)
        viewPager.setPageTransformer(VerticalViewPagerFragment.DepthPageTransformer())
        binding.dotsIndicator.attachTo(viewPager)
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(parentFragmentManager, lifecycle) {

        override fun getItemCount(): Int = weatherList.size

        override fun createFragment(position: Int): Fragment {
            return WeatherFragment.newInstance(weatherList[position])
        }
    }
}