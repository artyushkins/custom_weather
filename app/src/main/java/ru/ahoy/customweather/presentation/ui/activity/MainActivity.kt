package ru.ahoy.customweather.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import ru.ahoy.customweather.R
import ru.ahoy.customweather.databinding.ActivityMainBinding
import ru.ahoy.customweather.extension.findFragment
import ru.ahoy.customweather.extension.viewBinding
import ru.ahoy.customweather.presentation.ui.fragment.VerticalViewPagerFragment
import ru.ahoy.customweather.presentation.ui.interfaces.IVerticalViewPager

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val binding by viewBinding(ActivityMainBinding::class)
    private val searchView: SearchView by lazy { binding.searchView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, VerticalViewPagerFragment()).commit()
        searchView.setOnQueryTextListener(this)
        searchView.setOnQueryTextFocusChangeListener { _, isFocused ->
            if (isFocused) {
                showSearchFragment()
            } else {
                searchView.setQuery("", false)
                hideSearchFragment()
            }
        }
    }

    private fun showSearchFragment() {
        findFragment<IVerticalViewPager>()?.showSearchFragment(show = true)
    }

    private fun hideSearchFragment() {
        findFragment<IVerticalViewPager>()?.showSearchFragment(show = false)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e("sdsd", newText.toString())
        return false
    }
}