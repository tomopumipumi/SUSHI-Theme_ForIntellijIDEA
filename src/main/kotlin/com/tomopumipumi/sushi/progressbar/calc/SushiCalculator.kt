package com.tomopumipumi.sushi.progressbar.calc

import com.tomopumipumi.sushi.progressbar.model.SushiDrawState
import com.tomopumipumi.sushi.progressbar.model.SushiTheme

object SushiCalculator {
    fun updateState(
        state: SushiDrawState,
        width: Int,
        height: Int,
        amountFull: Float,
        isDeterminate: Boolean,
        currentTimeMs: Long,
        iconWidth: Int
    ) {
        val h = height.toFloat()
        val topH = h * 0.4f

        state.width = width
        state.height = height
        state.isDeterminate = isDeterminate
        state.amountFull = amountFull
        state.topFaceHeight = topH
        state.frontThickness = maxOf(2f, h * 0.1f)
        state.slant = h * 0.45f
        state.bottomY = h - state.frontThickness

        val ratio = h / SushiTheme.REFERENCE_HEIGHT
        state.plateWidth = iconWidth + 10f * ratio
        state.plateHeight = maxOf(10f, 18f * ratio)

        val speed = SushiTheme.SLAT_WIDTH / SushiTheme.BELT_SPEED_MS
        if (isDeterminate) {
            state.sushiLogicalX = amountFull
            state.beltOffset = amountFull
        } else {
            val loopRange = width + 160.0
            val continuousDistance = ((currentTimeMs.toDouble() * speed) % loopRange).toFloat()

            state.sushiLogicalX = continuousDistance - 80f
            state.beltOffset = continuousDistance
        }
    }
}