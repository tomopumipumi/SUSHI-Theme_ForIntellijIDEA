package com.tomopumipumi.sushi

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object SushiIcons {
    @JvmStatic
    fun getIcon(path: String) = IconLoader.getIcon(path, SushiIcons::class.java)

    @JvmField
    val STATUSBAR_SUSHI: Icon = getIcon("/icons/sushiTheme/statusbar_sushi.svg")

    @JvmField
    val PROGRESSBAR_SUSHI: Icon = getIcon("/icons/sushiTheme/progress_sushi.svg")
}