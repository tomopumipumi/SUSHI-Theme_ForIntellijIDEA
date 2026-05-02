package com.tomopumipumi.sushi.progressbar.model

import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import java.awt.Color


object SushiTheme {

    const val SLAT_WIDTH = 24f
    const val BELT_SPEED_MS = 300f
    const val REFERENCE_HEIGHT = 26f

    object Colors {
        val beltFront = JBColor(Color(205, 133, 63), Color(100, 65, 35))
        val beltBottomShadow = JBColor(Color(160, 100, 50), Color(40, 25, 15))
        val beltTopFace = JBColor(Color(222, 184, 135), Color(110, 90, 70))

        val beltInnerTrack = JBColor(Color(180, 180, 185), Color(60, 60, 65))

        val slatFace = JBColor(Gray._255, Color(130, 130, 125))
        val slatBorder = JBColor(Color(210, 210, 200), Color(80, 80, 75))

        val rivet = JBColor(Color(150, 150, 140), Color(50, 50, 45))

        val plateShadow = JBColor(Color(0, 0, 0, 60), Color(0, 0, 0, 120))
        val plateThick = JBColor(Color(150, 20, 20), Color(120, 15, 15))
        val plateRim = JBColor(Color(210, 45, 45), Color(180, 35, 35))
        val plateInner = JBColor(Gray._255, Gray._220)

        val progressOverlay = JBColor(Color(255, 165, 0, 50), Color(255, 150, 0, 40))
        val progressLed = JBColor(Color(255, 140, 0), Color(255, 160, 0))
    }
}