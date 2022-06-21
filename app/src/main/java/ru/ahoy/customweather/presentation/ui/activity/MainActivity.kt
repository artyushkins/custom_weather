package ru.ahoy.customweather.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import ru.ahoy.customweather.R
import ru.ahoy.customweather.databinding.ActivityMainBinding
import ru.ahoy.customweather.extension.findFragment
import ru.ahoy.customweather.extension.showFragment
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.fragment.VerticalFragment
import ru.ahoy.customweather.presentation.ui.fragment.VerticalViewPagerFragment
import ru.ahoy.customweather.presentation.ui.interfaces.IMainActivity
import ru.ahoy.customweather.presentation.ui.interfaces.IVerticalViewPager
import ru.ahoy.customweather.presentation.ui.interfaces.IWeatherFragment

class MainActivity : AppCompatActivity(), IMainActivity {

    private val binding by viewBinding(ActivityMainBinding::class)
    private val searchView: SearchView by lazy { binding.searchView }
    private val verticalViewPager by lazy { findFragment<IVerticalViewPager>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            showFragment(VerticalViewPagerFragment())
            binding.mainMenu.setOnClickListener {
                when (verticalViewPager.getCurrentFragment()) {
                    VerticalFragment.Weather -> {
                        if (findFragment<IWeatherFragment>().isStandalone) {
                            verticalViewPager.showCitiesFragment(show = true)
                        } else {
                            supportFragmentManager.popBackStack()
                            binding.mainMenu.icon = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_menu)
                        }
                    }
                    else -> verticalViewPager.showCitiesFragment(show = false)
                }
            }
            searchView.setOnQueryTextFocusChangeListener { _, isFocused ->
                if (isFocused) {
                    if (verticalViewPager.getCurrentFragment() == VerticalFragment.Cities) {
//                        showFragment(SearchFragment(), isAdd = true)
                    } else {
                        if (findFragment<IWeatherFragment>().isStandalone) {
                            supportFragmentManager.popBackStack()
                        } else {
                            showSearchFragment()
                        }
                    }
                } else {
                    if (verticalViewPager.getCurrentFragment() == VerticalFragment.Cities) {
//                        closeFragment()
                    } else {
                        if (!findFragment<IWeatherFragment>().isStandalone) {
                            searchView.setQuery("", false)
                            hideSearchFragment()
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        verticalViewPager.setViewPagerCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position != VerticalFragment.Weather.position) {
                    binding.mainMenu.icon = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_close)
                } else {
                    binding.mainMenu.icon = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_menu)
                }
            }
        })
    }

    override fun onBackPressed() {
        if (verticalViewPager.getCurrentFragment() != VerticalFragment.Weather) {
            verticalViewPager.showRootFragment()
        } else {
            super.onBackPressed()
        }
    }

    private fun showSearchFragment() {
        verticalViewPager.showSearchFragment(show = true)
        binding.mainMenu.isVisible = false
    }

    private fun hideSearchFragment() {
        verticalViewPager.showSearchFragment(show = false)
        binding.mainMenu.isVisible = true
    }

    override fun setQueryTextListener(listener: SearchView.OnQueryTextListener?) {
        searchView.setOnQueryTextListener(listener)
    }

    override fun onShowWeatherFromSearch() {
        searchView.clearFocus()
        binding.mainMenu.icon = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_close)
    }
}