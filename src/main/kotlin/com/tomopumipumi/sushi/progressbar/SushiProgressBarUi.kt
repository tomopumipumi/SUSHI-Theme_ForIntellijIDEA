@file:Suppress("RedundantSuppression")

package com.tomopumipumi.sushi.progressbar

import com.intellij.openapi.ui.GraphicsConfig
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI
import com.tomopumipumi.sushi.SushiIcons
import com.tomopumipumi.sushi.progressbar.calc.SushiCalculator
import com.tomopumipumi.sushi.progressbar.model.SushiDrawState
import com.tomopumipumi.sushi.progressbar.model.SushiTheme
import com.tomopumipumi.sushi.progressbar.renderer.SushiRenderer
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent
import javax.swing.Timer
import javax.swing.plaf.basic.BasicProgressBarUI

class SushiProgressBarUi : BasicProgressBarUI() {

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun createUI(@Suppress("unused") c: JComponent): SushiProgressBarUi {
            return SushiProgressBarUi()
        }
    }

    private val sushiIcon = SushiIcons.PROGRESSBAR_SUSHI

    private val drawState = SushiDrawState()
    private var customTimer: Timer? = null

    override fun startAnimationTimer() {
        if (customTimer == null) {
            customTimer = Timer(16) {
                progressBar?.repaint()
            }
        }
        customTimer?.start()
    }

    override fun stopAnimationTimer() {
        customTimer?.stop()
    }

    override fun getPreferredSize(c: JComponent?): Dimension {
        return Dimension(super.getPreferredSize(c).width, JBUI.scale(SushiTheme.REFERENCE_HEIGHT.toInt()))
    }

    override fun paintDeterminate(g: Graphics, c: JComponent) {
        val g2d = g.create() as Graphics2D
        try {
            val config = GraphicsConfig(g2d)
            GraphicsUtil.setupAAPainting(g2d)

            val width = progressBar.width
            val height = progressBar.height
            val amountFull = getAmountFull(progressBar.insets, width, height).toFloat()

            SushiCalculator.updateState(
                state = drawState,
                width = width,
                height = height,
                amountFull = amountFull,
                isDeterminate = true,
                currentTimeMs = System.currentTimeMillis(),
                iconWidth = sushiIcon.iconWidth
            )

            SushiRenderer.render(c, g2d, drawState, sushiIcon)

            config.restore()
        } finally {
            g2d.dispose()
        }
    }

    override fun paintIndeterminate(g: Graphics, c: JComponent) {
        val g2d = g.create() as Graphics2D
        try {
            val config = GraphicsConfig(g2d)
            GraphicsUtil.setupAAPainting(g2d)

            val width = progressBar.width
            val height = progressBar.height

            SushiCalculator.updateState(
                state = drawState,
                width = width,
                height = height,
                amountFull = 0f,
                isDeterminate = false,
                currentTimeMs = System.currentTimeMillis(),
                iconWidth = sushiIcon.iconWidth
            )

            SushiRenderer.render(c, g2d, drawState, sushiIcon)

            config.restore()
        } finally {
            g2d.dispose()
        }
    }

    override fun uninstallUI(c: JComponent?) {
        stopAnimationTimer()
        customTimer = null
        super.uninstallUI(c)
    }
}