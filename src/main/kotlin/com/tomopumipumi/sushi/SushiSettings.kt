package com.tomopumipumi.sushi

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "SushiSettings",
    storages = [Storage("SushiThemeSettings.xml")]
)
class SushiSettings : PersistentStateComponent<SushiSettings.State> {

    class State {
        var enableProgressBar = true
        var enableStatusBar = true
        var fps: Int = 30
        var bounceTopDistance: Int = 200
        var particleSpeedMultiplier: Float = 1.3f
        var feverSpawnCount: Int = 5
        var feverDurationMs: Int = 10000
        var comboTimeoutMs: Int = 1500
        var throttleMs: Int = 80
        var effectType: String = com.tomopumipumi.sushi.effect.EffectTypes.RANDOM
        var comboUnit: Int = 5
        var feverTriggerCombo: Int = 50
    }

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    companion object {
        val instance: SushiSettings
            get() = ApplicationManager.getApplication().getService(SushiSettings::class.java)
    }
}