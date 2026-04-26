package com.tomopumipumi.sushi

import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UIUtil
import javax.swing.JComponent

class SushiConfigurable : Configurable {
    private val progressBarCheckBox = JBCheckBox("Show ProgressBar")
    private val statusBarCheckBox = JBCheckBox("Show StatusBar")

    private val restartHintLabel = JBLabel("Please restart the IDE to apply changes.").apply {
        foreground = UIUtil.getContextHelpForeground()
    }

    override fun getDisplayName(): String = "SUSHI-Theme"

    override fun createComponent(): JComponent {
        return FormBuilder.createFormBuilder()
            .addComponent(progressBarCheckBox)
            .addComponent(statusBarCheckBox)
            .addVerticalGap(10)
            .addComponent(restartHintLabel)
            .addComponentFillVertically(javax.swing.JPanel(), 0)
            .panel
    }

    override fun isModified(): Boolean {
        val state = SushiSettings.instance.state
        return progressBarCheckBox.isSelected != state.enableProgressBar ||
                statusBarCheckBox.isSelected != state.enableStatusBar
    }

    override fun apply() {
        val state = SushiSettings.instance.state
        state.enableProgressBar = progressBarCheckBox.isSelected
        state.enableStatusBar = statusBarCheckBox.isSelected
    }

    override fun reset() {
        val state = SushiSettings.instance.state
        progressBarCheckBox.isSelected = state.enableProgressBar
        statusBarCheckBox.isSelected = state.enableStatusBar
    }
}