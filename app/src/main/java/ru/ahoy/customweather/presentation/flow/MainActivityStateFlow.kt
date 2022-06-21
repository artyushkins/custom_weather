package ru.ahoy.customweather.presentation.flow

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState

class MainActivityStateFlow {
    companion object {
        const val REPLAY = 10
    }

    private val flow = MutableSharedFlow<MainActivityState>(REPLAY)

    var state: MainActivityState get() = flow.replayCache.last()
        set(value) {
            if (flow.replayCache.isEmpty() || value::class != state::class) {
                flow.tryEmit(value)
            }
            Log.e("sdsd", flow.replayCache.joinToString { it::class.simpleName.toString() })
        }

    fun dropLast(callback: (() -> Unit)? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val newReplay = flow.replayCache.toMutableList()
                newReplay.removeAt(flow.replayCache.size - 1)
                resetCache()
                flow.emitAll(newReplay.asFlow())
                Log.e("sdsd", flow.replayCache.joinToString { it::class.simpleName.toString() })
                callback?.invoke()
            } catch (e: Exception) {
                Log.e(this::class.simpleName, e.localizedMessage.orEmpty())
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetCache() {
        flow.resetReplayCache()
    }

    suspend fun subscribe(block: suspend (MainActivityState) -> Unit) {
        flow.collectLatest { state ->
            block.invoke(state)
        }
    }
}