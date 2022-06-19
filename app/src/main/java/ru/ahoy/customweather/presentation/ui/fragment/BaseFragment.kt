package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import ru.ahoy.customweather.presentation.ui.interfaces.BaseFragmentInterface

abstract class BaseFragment : Fragment(), BaseFragmentInterface {
    protected abstract val binding: ViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }
}