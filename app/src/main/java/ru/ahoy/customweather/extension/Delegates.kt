package ru.ahoy.customweather.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty


fun <T : ViewBinding> AppCompatActivity.viewBinding(clazz: KClass<T>) = ActivityBindingDelegate(clazz)

@Suppress("UNCHECKED_CAST")
class ActivityBindingDelegate<T: ViewBinding>(private val clazz: KClass<T>) {
    private var value: T? = null

    operator fun getValue(activity: AppCompatActivity?, property: KProperty<*>): T {
        return value ?: try {
            activity?.lifecycle?.addObserver(ViewBindingObserver())
            (inflateFunction?.call(activity?.layoutInflater) as T).also(this::setValue)
        } catch (exception: Exception) {
            throw exception
        }
    }

    private val inflateFunction: KFunction<*>?
        get() = clazz.members
            .filterIsInstance<KFunction<*>>()
            .find { it.name.contains("inflate") }

    private fun setValue(value: T?) {
        this.value = value
    }

    inner class ViewBindingObserver : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            value = null
        }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(clazz: KClass<T>) = FragmentBindingDelegate(clazz)

@Suppress("UNCHECKED_CAST")
class FragmentBindingDelegate<T: ViewBinding>(private val clazz: KClass<T>) {
    private var value: T? = null

    operator fun getValue(fragment: Fragment?, property: KProperty<*>): T {
        return value ?: try {
            fragment?.lifecycle?.addObserver(ViewBindingObserver())
            (inflateFunction?.call(fragment?.layoutInflater) as T).also(this::setValue)
        } catch (exception: Exception) {
            throw exception
        }
    }

    private val inflateFunction: KFunction<*>?
        get() = clazz.members
            .filterIsInstance<KFunction<*>>()
            .find { it.name.contains("inflate") }

    private fun setValue(value: T?) {
        this.value = value
    }

    inner class ViewBindingObserver : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            value = null
        }
    }
}