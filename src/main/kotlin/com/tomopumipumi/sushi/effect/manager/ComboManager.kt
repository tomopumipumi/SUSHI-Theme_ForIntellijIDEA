package com.tomopumipumi.sushi.effect.manager

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiSettings
import kotlinx.coroutines.*
import java.awt.Point
import kotlin.time.Duration.Companion.milliseconds

class ComboManager(
    private val onUpdate: (combo: Int, position: Point, editor: Editor) -> Unit
) {
    private var comboCount = 0
    private var comboJob: Job? = null

    private var lastUpdateTime = 0L
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private fun resetCombo() {
        comboCount = 0
    }

    fun registerKeystroke(editor: Editor, position: Point) {
        comboCount++
        comboJob?.cancel()

        val state = SushiSettings.instance.state
        val resetMs = state.comboTimeoutMs.toLong()
        val throttleMs = state.throttleMs.toLong()

        val now = System.currentTimeMillis()

        comboJob = scope.launch {
            delay(resetMs.milliseconds)
            resetCombo()
        }

        if (now - lastUpdateTime >= throttleMs) {
            lastUpdateTime = now
            onUpdate(comboCount, position, editor)
        }
    }

    fun dispose() {
        comboJob?.cancel()
        scope.cancel()
    }
}