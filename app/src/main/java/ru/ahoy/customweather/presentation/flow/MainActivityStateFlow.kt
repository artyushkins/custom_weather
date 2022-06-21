package ru.ahoy.customweather.presentation.flow

import kotlinx.coroutines.flow.MutableSharedFlow
import ru.ahoy.customweather.presentation.ui.activity.MainActivityState

class MainActivityStateFlow {
    companion object {
        const val REPLAY = 2
    }

    private val flow = MutableSharedFlow<MainActivityState>(REPLAY)

    var state: MainActivityState get() = flow.replayCache.last()
        set(value) {
            if (flow.replayCache.isEmpty() || value::class != state::class) {
                flow.tryEmit(value)
            }
        }

    fun dropLast() {
        state = flow.replayCache[flow.replayCache.size - 2]
    }

    suspend fun subscribe(block: suspend (MainActivityState) -> Unit) {
        flow.collect { state ->
            block.invoke(state)
        }
    }
}