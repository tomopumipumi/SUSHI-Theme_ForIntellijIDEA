package com.tomopumipumi.sushi

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UIUtil
import com.tomopumipumi.sushi.effect.EffectTypes
import javax.swing.JComponent

class SushiConfigurable : Configurable {
    private val progressBarCheckBox = JBCheckBox(SushiBundle.message("settings.checkbox.progressBar"))
    private val statusBarCheckBox = JBCheckBox(SushiBundle.message("settings.checkbox.statusBar"))

    private val restartHintLabel = JBLabel(SushiBundle.message("settings.label.restart")).apply {
        foreground = UIUtil.getContextHelpForeground()
    }

    private val effectItems = listOf(
        EffectTypes.RANDOM to "effect.type.random",
        EffectTypes.MAGURO to "effect.type.maguro",
        EffectTypes.IKURA to "effect.type.ikura",
        EffectTypes.EBI to "effect.type.ebi",
        EffectTypes.MATCHA to "effect.type.matcha",
        EffectTypes.NONE to "effect.type.none"
    )
    private val effectTypeComboBox = ComboBox(effectItems.toTypedArray()).apply {
        renderer = SimpleListCellRenderer.create("") { item ->
            SushiBundle.message(item.second)
        }
    }

    private val fpsItems = listOf(
        15 to "fps.type.lightweight",
        30 to "fps.type.standard",
        60 to "fps.type.smooth",
        120 to "fps.type.ultrasmooth"
    )
    private val fpsTypeComboBox = ComboBox(fpsItems.toTypedArray()).apply {
        renderer = SimpleListCellRenderer.create("") { item ->
            SushiBundle.message(item.second)
        }
    }

    private val comboUnitField = JBTextField()
    private val feverTriggerComboField = JBTextField()
    private val comboTimeoutMsField = JBTextField()
    private val particleSpeedMultiplierField = JBTextField()
    private val bounceTopDistanceField = JBTextField()
    private val feverSpawnCountField = JBTextField()
    private val feverDurationMsField = JBTextField()

    override fun getDisplayName(): String = SushiBundle.message("settings.display.name")

    override fun createComponent(): JComponent {
        return FormBuilder.createFormBuilder()
            .addComponent(progressBarCheckBox)
            .addComponent(statusBarCheckBox)
            .addComponent(restartHintLabel)
            .addSeparator()
            .addLabeledComponent(SushiBundle.message("settings.label.effectType"), effectTypeComboBox)
            .addLabeledComponent(SushiBundle.message("settings.label.comboUnit"), comboUnitField)
            .addLabeledComponent(SushiBundle.message("settings.label.feverTriggerCombo"), feverTriggerComboField)
            .addLabeledComponent(SushiBundle.message("settings.label.comboTimeoutMs"), comboTimeoutMsField)
            .addLabeledComponent(SushiBundle.message("settings.label.fps"), fpsTypeComboBox)
            .addLabeledComponent(
                SushiBundle.message("settings.label.particleSpeedMultiplier"),
                particleSpeedMultiplierField
            )
            .addLabeledComponent(SushiBundle.message("settings.label.bounceTopDistance"), bounceTopDistanceField)
            .addLabeledComponent(SushiBundle.message("settings.label.feverSpawnCount"), feverSpawnCountField)
            .addLabeledComponent(SushiBundle.message("settings.label.feverDurationMs"), feverDurationMsField)
            .addComponentFillVertically(javax.swing.JPanel(), 0)
            .panel
    }

    override fun isModified(): Boolean {
        val state = SushiSettings.instance.state
        return progressBarCheckBox.isSelected != state.enableProgressBar ||
                statusBarCheckBox.isSelected != state.enableStatusBar ||
                getSelectedEffectType() != state.effectType ||
                getSelectedFps() != state.fps ||
                comboUnitField.text != state.comboUnit.toString() ||
                feverTriggerComboField.text != state.feverTriggerCombo.toString() ||
                comboTimeoutMsField.text != state.comboTimeoutMs.toString() ||
                particleSpeedMultiplierField.text != state.particleSpeedMultiplier.toString() ||
                bounceTopDistanceField.text != state.bounceTopDistance.toString() ||
                feverSpawnCountField.text != state.feverSpawnCount.toString() ||
                feverDurationMsField.text != state.feverDurationMs.toString()
    }

    override fun apply() {
        val state = SushiSettings.instance.state
        state.enableProgressBar = progressBarCheckBox.isSelected
        state.enableStatusBar = statusBarCheckBox.isSelected

        state.effectType = getSelectedEffectType()
        state.fps = getSelectedFps()

        comboUnitField.text.toIntOrNull()?.let { state.comboUnit = it }
        feverTriggerComboField.text.toIntOrNull()?.let { state.feverTriggerCombo = it }
        comboTimeoutMsField.text.toIntOrNull()?.let { state.comboTimeoutMs = it }
        particleSpeedMultiplierField.text.toFloatOrNull()?.let { state.particleSpeedMultiplier = it }
        bounceTopDistanceField.text.toIntOrNull()?.let { state.bounceTopDistance = it }
        feverSpawnCountField.text.toIntOrNull()?.let { state.feverSpawnCount = it }
        feverDurationMsField.text.toIntOrNull()?.let { state.feverDurationMs = it }
    }

    override fun reset() {
        val state = SushiSettings.instance.state
        progressBarCheckBox.isSelected = state.enableProgressBar
        statusBarCheckBox.isSelected = state.enableStatusBar

        effectTypeComboBox.selectedItem = effectItems.find { it.first == state.effectType } ?: effectItems[0]
        fpsTypeComboBox.selectedItem = fpsItems.find { it.first == state.fps } ?: fpsItems[1]

        comboUnitField.text = state.comboUnit.toString()
        feverTriggerComboField.text = state.feverTriggerCombo.toString()
        comboTimeoutMsField.text = state.comboTimeoutMs.toString()
        particleSpeedMultiplierField.text = state.particleSpeedMultiplier.toString()
        bounceTopDistanceField.text = state.bounceTopDistance.toString()
        feverSpawnCountField.text = state.feverSpawnCount.toString()
        feverDurationMsField.text = state.feverDurationMs.toString()
    }

    private fun getSelectedEffectType(): String {
        val pair = effectTypeComboBox.selectedItem as? Pair<*, *>
        return pair?.first as? String ?: EffectTypes.RANDOM
    }

    private fun getSelectedFps(): Int {
        val pair = fpsTypeComboBox.selectedItem as? Pair<*, *>
        return pair?.first as? Int ?: 30
    }
}