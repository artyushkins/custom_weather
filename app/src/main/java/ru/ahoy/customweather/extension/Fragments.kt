package ru.ahoy.customweather.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.ahoy.customweather.R
import ru.ahoy.customweather.presentation.ui.interfaces.BaseFragmentInterface
import kotlin.reflect.KClass

inline fun <reified I : BaseFragmentInterface> AppCompatActivity.findFragment(): I {
    return supportFragmentManager.fragments.filterIsInstance<I>().first()
}

fun AppCompatActivity.currentFragment(): KClass<out Fragment> {
    return supportFragmentManager.fragments.last()::class
}

fun AppCompatActivity.showFragment(fragment: Fragment, isAdd: Boolean = false) {
    supportFragmentManager.beginTransaction()
        .addOrReplace(isAdd, R.id.fragmentContainer, fragment)
        .commit()
}

private fun FragmentTransaction.addOrReplace(isAdd: Boolean, fragmentContainer: Int, fragment: Fragment): FragmentTransaction {
    return if (isAdd) {
        add(fragmentContainer, fragment)
    } else {
        replace(fragmentContainer, fragment)
    }
}

fun AppCompatActivity.closeFragment() {
    supportFragmentManager.popBackStack()
}