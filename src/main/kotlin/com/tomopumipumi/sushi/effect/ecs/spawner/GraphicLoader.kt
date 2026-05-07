package com.tomopumipumi.sushi.effect.ecs.spawner

import javax.swing.Icon
import kotlin.math.max
import kotlin.math.min

data class GraphicLevel(
    val width: Int,
    val height: Int,
    val icon: Icon
)

data class GraphicData(
    val width: Int,
    val height: Int,
    val icon: Icon
)


fun getGraphicData(levels: Array<GraphicLevel>, level: Int): GraphicData {
    val maxLevel = levels.size
    val safeLevel = min(max(level, 1), maxLevel)

    val targetLevel = levels[safeLevel - 1]

    return GraphicData(
        width = targetLevel.width,
        height = targetLevel.height,
        icon = targetLevel.icon
    )
}