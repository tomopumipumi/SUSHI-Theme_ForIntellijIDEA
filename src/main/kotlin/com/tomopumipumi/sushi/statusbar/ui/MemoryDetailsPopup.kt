package com.tomopumipumi.sushi.statusbar.ui

import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.util.ui.JBUI
import com.tomopumipumi.sushi.statusbar.domain.MemoryMonitor
import java.awt.Component
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel

object MemoryDetailsPopup {
    fun show(parentComponent: Component) {
        val usedMemory = MemoryMonitor.getUsedMemoryMB()
        val totalMemory = MemoryMonitor.getTotalMemoryMB()
        val maxMemory = MemoryMonitor.getMaxMemoryMB()

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
            .showInCenterOf(parentComponent)
    }
}