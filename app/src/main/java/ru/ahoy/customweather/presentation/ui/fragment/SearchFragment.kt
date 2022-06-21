package ru.ahoy.customweather.presentation.ui.fragment

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ahoy.customweather.databinding.FragmentSearchBinding
import ru.ahoy.customweather.extension.showFragment
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState
import ru.ahoy.customweather.presentation.ui.adapter.SearchAdapter
import ru.ahoy.customweather.presentation.ui.interfaces.ISearchFragment
import ru.ahoy.customweather.presentation.viewmodel.SearchViewModel

class SearchFragment : BaseFragment(), ISearchFragment, SearchView.OnQueryTextListener {

    companion object {
        fun newInstance(): SearchFragment = SearchFragment()
    }

    override val activityState: MainActivityState get() = MainActivityState.SearchScreen
    override val binding by viewBinding(FragmentSearchBinding::class)
    override val fragment: Fragment get() = this
    private val viewModel by viewModel<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val adapter = SearchAdapter(isRecent = false, this::showWeatherFragment)
        binding.cities.adapter = adapter
        binding.cities.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launchWhenResumed {
            viewModel.items.collect { items ->
                adapter.items = items
            }
        }
    }

    private fun showWeatherFragment(name: String) {
        showFragment(WeatherFragment.newInstance(null, name), isAdd = true)
    }

    override fun onResume() {
        super.onResume()
        activity.setQueryTextListener(this)
    }

    override fun onPause() {
        super.onPause()
        activity.setQueryTextListener(null)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (query.isNotEmpty()) viewModel.search(query)
        return false
    }
}