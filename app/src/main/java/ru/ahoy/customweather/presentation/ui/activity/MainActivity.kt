package ru.ahoy.customweather.presentation.ui.activity

import android.content.res.ColorStateList
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
import ru.ahoy.customweather.presentation.ui.fragment.CitiesFragment
import ru.ahoy.customweather.presentation.ui.fragment.HorizontalViewPagerFragment
import ru.ahoy.customweather.presentation.ui.fragment.SearchFragment
import ru.ahoy.customweather.presentation.ui.interfaces.IMainActivity
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

sealed class MainActivityState : ActivityState() {
    object MainWeatherScreen : MainActivityState()
    object SearchScreen : MainActivityState()
    object CitiesScreen : MainActivityState()
    object None : MainActivityState()
    sealed class DetailScreen : MainActivityState() {
        object AddWeather : DetailScreen()
        object None : DetailScreen()
    }
}

class MainActivity : AppCompatActivity(), IMainActivity {

    private val binding by viewBinding(ActivityMainBinding::class)
    private val searchView: SearchView by lazy { binding.searchView }
    private val activityStateFlow: MainActivityStateFlow by inject()
    private var activityState: ActivityState = MainActivityState.None


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeOnState()
        init(savedInstanceState)
    }

    override fun onBackPressed() {
        if (activityState !is MainActivityState.MainWeatherScreen) {
            closeFragment(activityStateFlow::dropLast)
        } else {
            super.onBackPressed()
        }
    }

    override fun setQueryTextListener(listener: SearchView.OnQueryTextListener?) {
        searchView.setOnQueryTextListener(listener)
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
                binding.mainBack.isVisible = false
                binding.mainMenu.isVisible = true
                binding.mainMenu.onClick { showCitiesFragment() }
                searchView.clearFocus()
                binding.mainAdd.isVisible = false
                binding.mainMenu.setIconResource(R.drawable.ic_menu)
            }
            is MainActivityState.SearchScreen -> {
                binding.mainBack.isVisible = true
                binding.mainAdd.isVisible = false
                binding.mainMenu.isVisible = false
            }
            is MainActivityState.DetailScreen -> {
                val fragment = findFragment<IWeatherFragment>()
                binding.mainBack.isVisible = true
                binding.mainAdd.isVisible = true
                binding.mainMenu.isVisible = false
                if (state == MainActivityState.DetailScreen.AddWeather) {
                    binding.mainAdd.iconTint = ColorStateList.valueOf(getColor(R.color.colorWhite))
                    binding.mainAdd.backgroundTintList = ColorStateList.valueOf(getColor(R.color.colorPositive))
                    binding.mainAdd.onClick { fragment?.removeWeather() }
                } else {
                    binding.mainAdd.iconTint = ColorStateList.valueOf(getColor(R.color.colorOnSurface))
                    binding.mainAdd.backgroundTintList = ColorStateList.valueOf(getColor(R.color.colorTransparent))
                    binding.mainAdd.onClick { fragment?.addWeather() }
                }
                searchView.clearFocus()
            }
            is MainActivityState.CitiesScreen -> {
                binding.mainBack.isVisible = false
                binding.mainMenu.setIconResource(R.drawable.ic_close)
                binding.mainMenu.isVisible = true
                binding.mainMenu.onClick { closeFragment(activityStateFlow::dropLast) }
                searchView.clearFocus()
                binding.mainAdd.isVisible = false
            }
            is MainActivityState.None -> { }
        }
    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            subscribeOnState()
            showRootFragment()
            binding.mainBack.onClick(this::onBackPressed)
            searchView.onFocused {
                if (activityState is MainActivityState.DetailScreen) {
                    closeFragment(activityStateFlow::dropLast)
                } else {
                    showSearchFragment()
                }
            }
        }
    }

    private fun showRootFragment() {
        activityStateFlow.resetCache()
        showFragment(HorizontalViewPagerFragment.newInstance())
    }

    private fun showCitiesFragment() {
        showFragment(CitiesFragment.newInstance(), isAdd = true)
    }

    private fun showSearchFragment() {
        showFragment(SearchFragment.newInstance(), isAdd = true)
    }
}