package com.tomopumipumi.sushi.statusbar

import java.awt.Component
import java.awt.Graphics
import javax.swing.Icon

public class RepeatingIcon(private val baseIcon: Icon, private val count: Int) : Icon {

    override fun getIconWidth(): Int = baseIcon.iconWidth * count

    override fun getIconHeight(): Int = baseIcon.iconHeight

    override fun paintIcon(c: Component?, g: Graphics?, x: Int, y: Int) {
        for (i in 0 until count) {
            baseIcon.paintIcon(c, g, x + (i * baseIcon.iconWidth), y)
        }
    }
}