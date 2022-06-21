package ru.ahoy.customweather.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.viewbinding.ViewBinding
import ru.ahoy.customweather.presentation.ui.interfaces.BaseFragmentInterface
import ru.ahoy.customweather.presentation.ui.interfaces.IMainActivity

abstract class BaseFragment : Fragment(), BaseFragmentInterface {
    protected abstract val binding: ViewBinding
    protected val activity: IMainActivity by lazy { requireActivity() as IMainActivity }

    override var lifecycleObserver: LifecycleObserver? = null
        set(value) {
            field = value
            lifecycleObserver?.let(lifecycle::addObserver)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onDestroyView() {
        lifecycleObserver?.let(lifecycle::removeObserver)
        super.onDestroyView()
    }
}