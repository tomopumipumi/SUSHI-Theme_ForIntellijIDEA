package com.tomopumipumi.sushi.effect.manager

import com.tomopumipumi.sushi.SushiSettings
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds

class FeverManager {
    var isFever: Boolean = false
        private set

    private val listeners = mutableListOf<(Boolean) -> Unit>()

    private var feverJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun onFeverStateChanged(listener: (Boolean) -> Unit) {
        listeners.add(listener)
    }

    fun removeFeverStateChangedListener(listener: (Boolean) -> Unit) {
        listeners.remove(listener)
    }

    fun start() {
        if (isFever) return

        isFever = true
        listeners.forEach { it(true) }

        feverJob?.cancel()

        val durationMs = SushiSettings.instance.state.feverDurationMs.toLong()

        feverJob = scope.launch {
            delay(durationMs.milliseconds)
            stop()
        }
    }

    fun stop() {
        if (!isFever) return

        isFever = false
        listeners.forEach { it(false) }

        feverJob?.cancel()
        feverJob = null
    }

    fun dispose() {
        stop()
        listeners.clear()
        scope.cancel()
    }
}