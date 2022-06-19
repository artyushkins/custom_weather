package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ru.ahoy.customweather.presentation.ui.interfaces.IVerticalViewPager
import kotlin.math.abs

class VerticalViewPagerFragment : Fragment(), IVerticalViewPager {

    private val viewPager: ViewPager2 by lazy { ViewPager2(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewPager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = ViewPagerAdapter()
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
//        viewPager.isUserInputEnabled = false
        viewPager.currentItem = 1
        viewPager.overScrollMode = ViewPager2.OVER_SCROLL_NEVER
        viewPager.setPageTransformer(DepthPageTransformer())
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(parentFragmentManager, lifecycle) {

        private val fragmentList = listOf(WeatherFragment(), WeatherFragment())

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
                    position <- 1 -> alpha = 0f
                    position <=1 -> {
                        val value = 1 - abs(position)
                        scaleX = minScale.coerceAtLeast(value)
                        scaleY = minScale.coerceAtLeast(value)
                        alpha = 1 - abs(position * 3f)
                    }
                    else -> alpha = 0f
                }
            }
        }
    }

    override fun showSearchFragment(show: Boolean) {
        viewPager.currentItem = if (show) 0 else 1
    }

}