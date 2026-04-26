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