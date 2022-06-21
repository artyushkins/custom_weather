package ru.ahoy.customweather.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.ahoy.customweather.R
import ru.ahoy.customweather.databinding.ActivityMainBinding
import ru.ahoy.customweather.extension.*
import ru.ahoy.customweather.presentation.flow.MainActivityStateFlow
import ru.ahoy.customweather.presentation.ui.fragment.BaseFragment
import ru.ahoy.customweather.presentation.ui.fragment.VerticalViewPagerFragment
import ru.ahoy.customweather.presentation.ui.interfaces.IMainActivity
import ru.ahoy.customweather.presentation.ui.interfaces.IVerticalViewPager
import ru.ahoy.customweather.presentation.ui.interfaces.IWeatherFragment

/**
 * App states:
 *  1. Main weather view pager
 *  2. Search screen
 *  3. Detail search screen
 *  4. List of cities
 *
 *  MainActivity must implement every state:
 *  1. Show menu button, show weather fragment
 *  2. Show search fragment, hide menu button
 *  3. SearchView still visible, show back button, show add button
 *  4. TODO
 * */

open class ActivityState

sealed class MainActivityState(val fragment: BaseFragment?): ActivityState() {
    class MainWeatherScreen(fragment: BaseFragment) : MainActivityState(fragment)
    class SearchScreen(fragment: BaseFragment) : MainActivityState(fragment)
    class DetailScreen(fragment: BaseFragment) : MainActivityState(fragment)
    class CitiesScreen(fragment: BaseFragment) : MainActivityState(fragment)
    object None : MainActivityState(null)
}

class MainActivity : AppCompatActivity(), IMainActivity {

    private val binding by viewBinding(ActivityMainBinding::class)
    private val searchView: SearchView by lazy { binding.searchView }
    private val verticalViewPager by lazy { findFragment<IVerticalViewPager>() }
    private val activityStateFlow: MainActivityStateFlow by inject()
    private var activityState: ActivityState = MainActivityState.None


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeOnState()
        init(savedInstanceState)
    }

    private fun subscribeOnState() {
        lifecycleScope.launchWhenCreated {
            activityStateFlow.subscribe { state ->
                withContext(Dispatchers.Main) {
                    obtainState(state = state)
                }
            }
        }
    }

    private fun obtainState(state: MainActivityState) {
        activityState = state
        when (state) {
            is MainActivityState.MainWeatherScreen -> {
                binding.mainMenu.setIconResource(R.drawable.ic_menu)
                binding.mainMenu.onClick { verticalViewPager.showCitiesFragment(show = true) }
                binding.mainMenu.isVisible = true
                binding.mainBack.isVisible = false
                searchView.clearFocus()
            }
            is MainActivityState.SearchScreen -> {
                binding.mainMenu.setIconResource(R.drawable.ic_close)
                binding.mainBack.isVisible = true
                binding.mainMenu.isVisible = false
            }
            is MainActivityState.DetailScreen -> {
                binding.mainMenu.setIconResource(R.drawable.ic_launcher_foreground)
                binding.mainBack.isVisible = true
                searchView.clearFocus()
            }
            is MainActivityState.CitiesScreen -> {
                binding.mainMenu.setIconResource(R.drawable.ic_close)
                binding.mainMenu.onClick { verticalViewPager.showCitiesFragment(show = false) }
                binding.mainBack.isVisible = false
                searchView.clearFocus()
            }
            else -> { }
        }
    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            subscribeOnState()
            showFragment(VerticalViewPagerFragment())
            binding.mainBack.onClick(this::onBackPressed)
            searchView.onFocused {
                if (findFragment<IWeatherFragment>().isStandalone) {
                    supportFragmentManager.popBackStack()
                } else {
                    showSearchFragment()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (activityState is MainActivityState.DetailScreen) {
            (activityState as MainActivityState.DetailScreen).fragment?.let { fragment ->
                supportFragmentManager.beginTransaction().remove(fragment).commit()
                activityStateFlow.dropLast()
            }
        } else {
            if (activityState !is MainActivityState.MainWeatherScreen) {
                verticalViewPager.showRootFragment()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun showSearchFragment() {
        verticalViewPager.showSearchFragment(show = true)
    }

    override fun setQueryTextListener(listener: SearchView.OnQueryTextListener?) {
        searchView.setOnQueryTextListener(listener)
    }
}