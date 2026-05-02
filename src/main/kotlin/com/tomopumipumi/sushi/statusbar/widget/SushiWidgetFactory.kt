package com.tomopumipumi.sushi.statusbar.widget

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.tomopumipumi.sushi.SushiSettings

class SushiWidgetFactory : StatusBarWidgetFactory {
    override fun getId(): String = "sushi"

    override fun getDisplayName(): @NlsContexts.ConfigurableName String {
        return "Sushi Plates Status"
    }

    override fun isAvailable(project: Project): Boolean {
        return SushiSettings.instance.state.enableStatusBar
    }

    override fun createWidget(
        project: Project
    ): StatusBarWidget {
        return SushiWidget(project)
    }

    override fun disposeWidget(widget: StatusBarWidget) {

    }

    override fun canBeEnabledOn(statusBar: StatusBar): Boolean {
        return true
    }
}