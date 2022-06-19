package ru.ahoy.customweather.presentation.ui.interfaces

import androidx.viewpager2.widget.ViewPager2
import ru.ahoy.customweather.presentation.ui.fragment.VerticalFragment

interface IVerticalViewPager : BaseFragmentInterface {
    fun showSearchFragment(show: Boolean)
    fun showCitiesFragment(show: Boolean)
    fun showRootFragment()
    fun getCurrentFragment(): VerticalFragment
    fun setViewPagerCallback(callback: ViewPager2.OnPageChangeCallback)
}