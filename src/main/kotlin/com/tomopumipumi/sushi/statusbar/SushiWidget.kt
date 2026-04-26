package com.tomopumipumi.sushi.statusbar

import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.ui.components.JBLabel
import com.intellij.util.Consumer
import com.intellij.util.ui.JBUI
import com.tomopumipumi.sushi.SushiIcons
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.Timer

const val MEGABYTE = 1024L * 1024L

fun getUsedMemoryMB(): Long {
    val runtime = Runtime.getRuntime()
    return (runtime.totalMemory() - runtime.freeMemory()) / MEGABYTE
}

class SushiWidget : CustomStatusBarWidget {
    companion object {
        const val ID = "SushiPlateWidget"
        private const val MB_PER_SUSHI = 250L
        private const val MAX_SUSHI = 15
    }

    private var myStatusBar: StatusBar? = null
    private var timer: Timer? = null

    private val label = JBLabel().apply {
        border = JBUI.Borders.empty(0, 2)
    }

    private val baseSushiIcon: Icon = SushiIcons.STATUSBAR_SUSHI

    override fun install(statusBar: StatusBar) {
        this.myStatusBar = statusBar
        label.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                showMemoryDetailsPopup(e)
            }
        })

        Timer(2000) {
            updateUI()
        }.apply { start() }.also { timer = it }

        updateUI()
    }

    override fun getComponent(): JComponent? = label

    private fun updateUI() {
        val usedMB = getUsedMemoryMB()
        val sushiCount = (usedMB / MB_PER_SUSHI).toInt().coerceIn(1, MAX_SUSHI)

        label.icon = RepeatingIcon(baseSushiIcon, sushiCount)
        label.toolTipText = "Memory Usage: ${usedMB}MB"

        label.repaint()
    }

    override fun ID(): String = ID

    private fun showMemoryDetailsPopup(event: MouseEvent) {
        val runtime = Runtime.getRuntime()
        val maxMemory = runtime.maxMemory() / MEGABYTE
        val totalMemory = runtime.totalMemory() / MEGABYTE
        val usedMemory = getUsedMemoryMB()

        val panel = JPanel(GridLayout(3, 2, 10, 5)).apply {
            border = JBUI.Borders.empty(10)

            add(JLabel("Used:"))
            add(JLabel("${usedMemory}M").apply { horizontalAlignment = JLabel.RIGHT })

            add(JLabel("Committed:"))
            add(JLabel("${totalMemory}M").apply { horizontalAlignment = JLabel.RIGHT })

            add(JLabel("Max:"))
            add(JLabel("${maxMemory}M").apply { horizontalAlignment = JLabel.RIGHT })
        }

        JBPopupFactory.getInstance()
            .createComponentPopupBuilder(panel, null)
            .setTitle("🍣 Memory Details")
            .setMovable(true)
            .setRequestFocus(true)
            .createPopup()
            .showInCenterOf(event.component)
    }

    override fun dispose() {
        timer?.stop()
        timer = null
        myStatusBar = null
    }
}