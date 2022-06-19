package ru.ahoy.customweather.presentation.ui.activity

import android.os.Bundle
import android.util.Log
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
import ru.ahoy.customweather.presentation.ui.interfaces.IVerticalViewPager

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

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
                if (verticalViewPager.getCurrentFragment() == VerticalFragment.Weather) {
                    verticalViewPager.showCitiesFragment(show = true)
                } else {
                    verticalViewPager.showCitiesFragment(show = false)
                }
            }
            searchView.setOnQueryTextListener(this)
            searchView.setOnQueryTextFocusChangeListener { _, isFocused ->
                if (isFocused) {
                    if (verticalViewPager.getCurrentFragment() == VerticalFragment.Cities) {
//                        showFragment(SearchFragment(), isAdd = true)
                    } else {
                        showSearchFragment()
                    }
                } else {
                    if (verticalViewPager.getCurrentFragment() == VerticalFragment.Cities) {
//                        closeFragment()
                    } else {
                        searchView.setQuery("", false)
                        hideSearchFragment()
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e("sdsd", newText.toString())
        return false
    }
}