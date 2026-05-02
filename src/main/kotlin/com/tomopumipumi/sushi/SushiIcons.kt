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

    @JvmField
    val FEVER_LV1: Icon = getIcon("/icons/sushiTheme/effectSVG/fever/lv1.svg")
    @JvmField
    val FEVER_LV2: Icon = getIcon("/icons/sushiTheme/effectSVG/fever/lv2.svg")
    @JvmField
    val FEVER_LV3: Icon = getIcon("/icons/sushiTheme/effectSVG/fever/lv3.svg")
    @JvmField
    val FEVER_LV4: Icon = getIcon("/icons/sushiTheme/effectSVG/fever/lv4.svg")
    @JvmField
    val FEVER_LV5: Icon = getIcon("/icons/sushiTheme/effectSVG/fever/lv5.svg")

    @JvmField
    val EBI_LV1: Icon = getIcon("/icons/sushiTheme/effectSVG/ebi/lv1.svg")
    @JvmField
    val EBI_LV2: Icon = getIcon("/icons/sushiTheme/effectSVG/ebi/lv2.svg")
    @JvmField
    val EBI_LV3: Icon = getIcon("/icons/sushiTheme/effectSVG/ebi/lv3.svg")
    @JvmField
    val EBI_LV4: Icon = getIcon("/icons/sushiTheme/effectSVG/ebi/lv4.svg")
    @JvmField
    val EBI_LV5: Icon = getIcon("/icons/sushiTheme/effectSVG/ebi/lv5.svg")

    @JvmField
    val MAGURO_LV1: Icon = getIcon("/icons/sushiTheme/effectSVG/maguro/lv1.svg")
    @JvmField
    val MAGURO_LV2: Icon = getIcon("/icons/sushiTheme/effectSVG/maguro/lv2.svg")
    @JvmField
    val MAGURO_LV3: Icon = getIcon("/icons/sushiTheme/effectSVG/maguro/lv3.svg")
    @JvmField
    val MAGURO_LV4: Icon = getIcon("/icons/sushiTheme/effectSVG/maguro/lv4.svg")
    @JvmField
    val MAGURO_LV5: Icon = getIcon("/icons/sushiTheme/effectSVG/maguro/lv5.svg")

    @JvmField
    val IKURA_LV1: Icon = getIcon("/icons/sushiTheme/effectSVG/ikura/lv1.svg")
    @JvmField
    val IKURA_LV2: Icon = getIcon("/icons/sushiTheme/effectSVG/ikura/lv2.svg")
    @JvmField
    val IKURA_LV3: Icon = getIcon("/icons/sushiTheme/effectSVG/ikura/lv3.svg")
    @JvmField
    val IKURA_LV4: Icon = getIcon("/icons/sushiTheme/effectSVG/ikura/lv4.svg")
    @JvmField
    val IKURA_LV5: Icon = getIcon("/icons/sushiTheme/effectSVG/ikura/lv5.svg")

    @JvmField
    val MATCHA_LV1: Icon = getIcon("/icons/sushiTheme/effectSVG/matcha/lv1.svg")
    @JvmField
    val MATCHA_LV2: Icon = getIcon("/icons/sushiTheme/effectSVG/matcha/lv2.svg")
    @JvmField
    val MATCHA_LV3: Icon = getIcon("/icons/sushiTheme/effectSVG/matcha/lv3.svg")
    @JvmField
    val MATCHA_LV4: Icon = getIcon("/icons/sushiTheme/effectSVG/matcha/lv4.svg")
    @JvmField
    val MATCHA_LV5: Icon = getIcon("/icons/sushiTheme/effectSVG/matcha/lv5.svg")
}