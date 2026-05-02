package com.tomopumipumi.sushi.progressbar.renderer

import com.tomopumipumi.sushi.progressbar.model.SushiDrawState
import com.tomopumipumi.sushi.progressbar.model.SushiTheme
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.RoundRectangle2D
import javax.swing.Icon
import javax.swing.JComponent


object SushiRenderer {

    fun render(c: JComponent, g2d: Graphics2D, state: SushiDrawState, sushiIcon: Icon) {
        drawConveyorBelt(g2d, state)
        if (state.isDeterminate && state.amountFull > 0) drawDeterminateProgress(g2d, state)
        drawSushiWithPlate(c, g2d, state, sushiIcon)
    }

    private fun drawConveyorBelt(g2d: Graphics2D, state: SushiDrawState) {
        val w = state.width.toFloat()
        val topH = state.topFaceHeight
        val frontT = state.frontThickness
        val slant = state.slant
        val bottomY = state.bottomY
        val colors = SushiTheme.Colors

        g2d.color = colors.beltFront
        g2d.fill(RoundRectangle2D.Float(0f, bottomY, w, frontT, 4f, 4f))

        g2d.color = colors.beltBottomShadow
        g2d.fillRect(0, (bottomY + frontT - 2f).toInt(), state.width, 2)

        val oldTransform = g2d.transform
        g2d.translate(0.0, bottomY.toDouble())
        g2d.shear(-(slant / topH).toDouble(), 0.0)

        g2d.color = colors.beltTopFace
        g2d.fill(RoundRectangle2D.Float(-slant * 2, -topH, w + slant * 4, topH, 4f, 4f))

        val trackMargin = 2f
        val innerTrack = RoundRectangle2D.Float(
            -slant * 2 + trackMargin,
            -topH + trackMargin,
            w + slant * 4 - trackMargin * 2,
            topH - trackMargin * 2,
            4f,
            4f
        )
        g2d.color = colors.beltInnerTrack
        g2d.fill(innerTrack)

        val oldClip = g2d.clip
        g2d.clip(innerTrack)

        val slatWidth = SushiTheme.SLAT_WIDTH
        val shift = state.beltOffset % slatWidth
        var x = -slant * 2 - slatWidth * 2 + shift

        while (x <= w + slant * 2 + slatWidth) {
            val slatShape =
                RoundRectangle2D.Float(x, -topH + trackMargin + 1f, slatWidth - 2f, topH - trackMargin * 2 - 2f, 4f, 4f)

            g2d.color = colors.slatFace
            g2d.fill(slatShape)

            g2d.color = colors.slatBorder
            g2d.draw(slatShape)

            g2d.color = colors.rivet
            g2d.fill(Ellipse2D.Float(x + slatWidth - 6f, -topH / 2f - 1.5f, 3f, 3f))

            x += slatWidth
        }

        g2d.clip = oldClip
        g2d.transform = oldTransform
    }

    private fun drawDeterminateProgress(g2d: Graphics2D, state: SushiDrawState) {
        val fillWidth = state.amountFull
        val topH = state.topFaceHeight
        val slant = state.slant
        val bottomY = state.bottomY
        val colors = SushiTheme.Colors

        val oldTrans = g2d.transform
        g2d.translate(0.0, bottomY.toDouble())
        g2d.shear(-(slant / topH).toDouble(), 0.0)

        val margin = 2f
        g2d.color = colors.progressOverlay
        g2d.fill(
            RoundRectangle2D.Float(
                margin,
                -topH + margin,
                maxOf(0f, fillWidth - margin * 2),
                topH - margin * 2,
                4f,
                4f
            )
        )

        g2d.transform = oldTrans

        g2d.color = colors.progressLed
        g2d.fill(RoundRectangle2D.Float(0f, bottomY + 1f, fillWidth, state.frontThickness - 2f, 2f, 2f))
    }

    private fun drawSushiWithPlate(c: JComponent, g2d: Graphics2D, state: SushiDrawState, sushiIcon: Icon) {
        val logicalY = -state.topFaceHeight / 2f
        val plateLogicalX = state.sushiLogicalX - state.plateWidth / 2f
        val plateLogicalY = logicalY - state.plateHeight / 2f
        val colors = SushiTheme.Colors

        val oldTransform = g2d.transform
        g2d.translate(0.0, state.bottomY.toDouble())
        g2d.shear(-(state.slant / state.topFaceHeight).toDouble(), 0.0)

        g2d.color = colors.plateShadow
        g2d.fill(Ellipse2D.Float(plateLogicalX + 2f, plateLogicalY + 2f, state.plateWidth, state.plateHeight))

        g2d.color = colors.plateThick
        g2d.fill(Ellipse2D.Float(plateLogicalX, plateLogicalY + 1f, state.plateWidth, state.plateHeight))

        g2d.color = colors.plateRim
        g2d.fill(Ellipse2D.Float(plateLogicalX, plateLogicalY, state.plateWidth, state.plateHeight))

        val margin = 2f
        g2d.color = colors.plateInner
        g2d.fill(
            Ellipse2D.Float(
                plateLogicalX + margin,
                plateLogicalY + margin,
                state.plateWidth - margin * 2f,
                state.plateHeight - margin * 2f
            )
        )

        g2d.transform = oldTransform

        val screenX = state.sushiLogicalX + (-state.slant / state.topFaceHeight) * logicalY
        val screenY = state.bottomY + logicalY

        val iconX = screenX - sushiIcon.iconWidth / 2f
        val iconY = screenY - sushiIcon.iconHeight + 4f

        val oldTrans2 = g2d.transform
        g2d.translate(iconX.toDouble(), iconY.toDouble())
        sushiIcon.paintIcon(c, g2d, 0, 0)
        g2d.transform = oldTrans2
    }
}