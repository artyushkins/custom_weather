package ru.ahoy.customweather.extension

import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import ru.ahoy.customweather.R
import ru.ahoy.customweather.presentation.ui.activity.MainActivity
import ru.ahoy.customweather.presentation.ui.fragment.BaseFragment
import ru.ahoy.customweather.presentation.ui.interfaces.BaseFragmentInterface


inline fun <reified I : BaseFragmentInterface> AppCompatActivity.findFragment(): I? {
    return supportFragmentManager.fragments.filterIsInstance<I>().takeIf { it.isNotEmpty() }?.last()
}

fun FragmentActivity.showFragment(fragment: BaseFragment, isAdd: Boolean = false): BaseFragmentInterface {
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        .addOrReplace(isAdd, R.id.fragmentContainer, fragment)
        .commit()
    return fragment
}

fun Fragment.showFragment(fragment: BaseFragment, isAdd: Boolean = false) {
    requireActivity().showFragment(fragment, isAdd)
}

private fun FragmentTransaction.addOrReplace(isAdd: Boolean, fragmentContainer: Int, fragment: Fragment): FragmentTransaction {
    return if (isAdd) {
        add(fragmentContainer, fragment)
    } else {
        replace(fragmentContainer, fragment)
    }
}

fun MainActivity.closeFragment(onCloseCallback: () -> Unit) {
    onCloseCallback.invoke()
    val fragment = supportFragmentManager.fragments.last()
    if (!fragment.isAdded) {
        supportFragmentManager.popBackStack()
    } else {
        removeFragment(fragment as? BaseFragment ?: throw ClassCastException("Fragment must implement BaseFragment"))
    }
}

fun FragmentActivity.removeFragment(fragment: BaseFragment) {
    val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
    animation.duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) { }

        override fun onAnimationStart(p0: Animation?) { }

        override fun onAnimationEnd(animation: Animation?) {
            try {
                supportFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss()
            } catch (e: Exception) {
                Log.e("removeFragment()", e.localizedMessage.orEmpty())
            }
        }
    })
    fragment.view?.startAnimation(animation)
}
