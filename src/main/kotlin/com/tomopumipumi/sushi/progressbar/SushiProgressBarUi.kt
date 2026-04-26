package com.tomopumipumi.sushi.progressbar

import com.intellij.openapi.ui.GraphicsConfig
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.JBColor
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI
import com.tomopumipumi.sushi.SushiIcons
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D
import javax.swing.JComponent
import javax.swing.plaf.basic.BasicProgressBarUI

class SushiProgressBarUi : BasicProgressBarUI() {

    companion object {
        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        fun createUI(c: JComponent): SushiProgressBarUi {
            return SushiProgressBarUi()
        }
    }

    private val sushiIcon = SushiIcons.PROGRESSBAR_SUSHI

    override fun getPreferredSize(c: JComponent?): Dimension {
        return Dimension(super.getPreferredSize(c).width, JBUI.scale(20))
    }

    override fun paintDeterminate(g: Graphics, c: JComponent) {
        val g2d = g.create() as Graphics2D
        try {
            val config = GraphicsConfig(g2d)
            GraphicsUtil.setupAAPainting(g2d)

            val width = progressBar.width
            val height = progressBar.height
            val amountFull = getAmountFull(progressBar.insets, width, height)

            // Background
            g2d.color = JBColor.namedColor("ProgressBar.trackColor", JBColor.LIGHT_GRAY)
            g2d.fill(RoundRectangle2D.Float(0f, 0f, width.toFloat(), height.toFloat(), 8f, 8f))

            // ProgressBar
            g2d.color = JBColor.namedColor("ProgressBar.progressColor", JBColor.ORANGE)
            g2d.fill(RoundRectangle2D.Float(0f, 0f, amountFull.toFloat(), height.toFloat(), 8f, 8f))

            // Sushi
            val iconX = amountFull - sushiIcon.iconWidth
            val iconY = (height - sushiIcon.iconHeight) / 2

            if (amountFull > 0) sushiIcon.paintIcon(c, g2d, maxOf(0, iconX), iconY)

            config.restore()
        } finally {
            g2d.dispose()
        }
    }

    override fun paintIndeterminate(g: Graphics, c: JComponent) {
        paintDeterminate(g, c)
    }

}