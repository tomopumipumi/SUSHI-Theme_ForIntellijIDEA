package com.tomopumipumi.sushi.statusbar.ui

import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBUI
import com.tomopumipumi.sushi.SushiIcons
import java.awt.Color
import javax.swing.BoxLayout
import javax.swing.JPanel

class SushiWidgetPanel : JPanel() {
    private val leftIconLabel = JBLabel()
    private val textLabel = JBLabel()
    private val rightIconLabel = JBLabel()

    private val baseSushiIcon = SushiIcons.STATUSBAR_SUSHI

    private val feverColors = arrayOf(
        JBColor(Color(220, 0, 0), Color(255, 100, 100)),
        JBColor(Color(220, 100, 0), Color(255, 170, 50)),
        JBColor(Color(180, 150, 0), Color(255, 255, 85)),
        JBColor(Color(0, 150, 0), Color(100, 255, 100)),
        JBColor(Color(0, 150, 180), Color(100, 255, 255)),
        JBColor(Color(180, 0, 180), Color(255, 100, 255))
    )

    private val feverIcons = arrayOf(
        SushiIcons.FEVER_LV1,
        SushiIcons.FEVER_LV2,
        SushiIcons.FEVER_LV3,
        SushiIcons.FEVER_LV4,
        SushiIcons.FEVER_LV5
    )

    init {
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        isOpaque = false
        border = JBUI.Borders.empty(0, 2)

        add(leftIconLabel)
        add(textLabel)
        add(rightIconLabel)
    }

    fun renderNormalState(sushiCount: Int, usedMB: Long) {
        leftIconLabel.icon = RepeatingIcon(baseSushiIcon, sushiCount)
        textLabel.text = ""
        textLabel.foreground = null
        rightIconLabel.icon = null

        toolTipText = "Memory Usage: ${usedMB}MB"
        repaint()
    }

    fun renderFeverState(frame: Int) {
        textLabel.foreground = feverColors[frame % feverColors.size]
        textLabel.text = "  FEVER TIME !!!  "

        val currentSvgIcon = feverIcons[(frame / 3) % feverIcons.size]
        leftIconLabel.icon = currentSvgIcon
        rightIconLabel.icon = currentSvgIcon
    }
}