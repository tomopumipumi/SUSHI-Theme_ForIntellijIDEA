package com.tomopumipumi.sushi.statusbar.widget

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBar
import com.tomopumipumi.sushi.effect.SushiEffectController
import com.tomopumipumi.sushi.statusbar.domain.MemoryMonitor
import com.tomopumipumi.sushi.statusbar.ui.MemoryDetailsPopup
import com.tomopumipumi.sushi.statusbar.ui.SushiWidgetPanel
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.Timer

class SushiWidget(private val project: Project) : CustomStatusBarWidget {
    companion object {
        const val ID = "sushi"
    }

    private val view = SushiWidgetPanel()

    private var normalTimer: Timer? = null
    private var feverTimer: Timer? = null

    private var isFever = false
    private var feverFrame = 0

    private val feverListener: (Boolean) -> Unit = { fever ->
        isFever = fever
        if (fever) startFever() else stopFever()
    }

    override fun install(statusBar: StatusBar) {
        view.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                MemoryDetailsPopup.show(e.component)
            }
        })

        normalTimer = Timer(2000) {
            updateNormalUI()
        }.apply { start() }

        project.service<SushiEffectController>().effectManager.feverManager.onFeverStateChanged(feverListener)

        updateNormalUI()
    }

    override fun getComponent(): JComponent = view

    override fun ID(): String = ID

    private fun updateNormalUI() {
        if (isFever) return
        val usedMB = MemoryMonitor.getUsedMemoryMB()
        val sushiCount = MemoryMonitor.calculateSushiCount()
        view.renderNormalState(sushiCount, usedMB)
    }

    private fun startFever() {
        normalTimer?.stop()
        feverFrame = 0

        feverTimer = Timer(40) {
            view.renderFeverState(feverFrame)
            feverFrame++
        }.apply { start() }
    }

    private fun stopFever() {
        feverTimer?.stop()
        feverTimer = null

        normalTimer?.start()
        updateNormalUI()
    }

    override fun dispose() {
        normalTimer?.stop()
        normalTimer = null

        feverTimer?.stop()
        feverTimer = null

        project.service<SushiEffectController>().effectManager.feverManager.removeFeverStateChangedListener(
            feverListener
        )
    }
}