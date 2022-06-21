package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ru.ahoy.customweather.databinding.FragmentStubBinding
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState
import ru.ahoy.customweather.presentation.ui.interfaces.IVerticalViewPager
import kotlin.math.abs

sealed class VerticalFragment(val position: Int) {
    object Search : VerticalFragment(position = 0)
    object Weather : VerticalFragment(position = 1)
    object Cities : VerticalFragment(position = 2)
}

class VerticalViewPagerFragment : BaseFragment(), IVerticalViewPager {

    companion object {
        fun getVerticalFragment(@IntRange(from = 0, to = 2) position: Int): VerticalFragment {
            return when (position) {
                0 -> VerticalFragment.Search
                1 -> VerticalFragment.Weather
                2 -> VerticalFragment.Cities
                else -> throw IllegalArgumentException("Invalid fragment position")
            }
        }
    }

    override val fragment: Fragment get() = this
    override val binding: ViewBinding by viewBinding(FragmentStubBinding::class) // Stub
    override val activityState: MainActivityState get() = MainActivityState.None
    private val viewPager: ViewPager2 by lazy { ViewPager2(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewPager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.isUserInputEnabled = false
        viewPager.adapter = ViewPagerAdapter()
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.overScrollMode = ViewPager2.OVER_SCROLL_NEVER
        viewPager.setCurrentItem(VerticalFragment.Weather.position, false)
        viewPager.setPageTransformer(DepthPageTransformer())
    }

    override fun showSearchFragment(show: Boolean) {
        viewPager.currentItem = if (show) VerticalFragment.Search.position else VerticalFragment.Weather.position
    }

    override fun showCitiesFragment(show: Boolean) {
        viewPager.currentItem = if (show) VerticalFragment.Cities.position else VerticalFragment.Weather.position
    }

    override fun showRootFragment() {
        viewPager.currentItem = VerticalFragment.Weather.position
    }

    override fun getCurrentFragment(): VerticalFragment {
        return getVerticalFragment(viewPager.currentItem)
    }

    override fun setViewPagerCallback(callback: ViewPager2.OnPageChangeCallback) {
        viewPager.registerOnPageChangeCallback(callback)
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(parentFragmentManager, lifecycle) {

        private val fragmentList = listOf(SearchFragment(), HorizontalViewPagerFragment(), CitiesFragment())

        override fun getItemCount(): Int = fragmentList.size

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    class DepthPageTransformer : ViewPager2.PageTransformer {

        private val minScale = 0.25f
        override fun transformPage(view: View, position: Float) {
            view.apply {
                when {
                    position < -1 -> alpha = 0f
                    position <= 1 -> {
                        val value = 1 - abs(position)
                        scaleX = minScale.coerceAtLeast(value)
                        scaleY = minScale.coerceAtLeast(value)
                        alpha = 1 - abs(position * 30f)
                    }
                    else -> alpha = 0f
                }
            }
        }
    }

}