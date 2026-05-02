package com.tomopumipumi.sushi.effect.ecs.spawner

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiIcons
import com.tomopumipumi.sushi.effect.ecs.Registry
import java.awt.Point
import kotlin.random.Random

private val EBI_GRAPHICS = arrayOf(
    GraphicLevel(12, 12, SushiIcons.EBI_LV1),
    GraphicLevel(18, 12, SushiIcons.EBI_LV2),
    GraphicLevel(24, 14, SushiIcons.EBI_LV3),
    GraphicLevel(24, 16, SushiIcons.EBI_LV4),
    GraphicLevel(26, 18, SushiIcons.EBI_LV5)
)

fun spawnEbi(
    registry: Registry,
    editor: Editor,
    position: Point,
    level: Int,
    speedMultiplier: Float = 1.0f
) {
    val graphic = getGraphicData(EBI_GRAPHICS, level)
    val count = Random.nextInt(2, 4)

    val p = registry.physics
    val l = registry.lifecycle

    repeat(count) {
        val entity = registry.createBaseParticle(editor, position, graphic.icon, graphic.width, graphic.height)
        if (entity == -1) return@repeat

        p.vx[entity] = (Random.nextFloat() - 0.5f) * 15f * speedMultiplier
        p.vy[entity] = -(Random.nextFloat() * 15f + 10f) * speedMultiplier

        l.life[entity] = 20f
        l.maxLife[entity] = 20f

        p.gravity[entity] = 1.2f
        p.friction[entity] = 0.98f
        p.rotationFactor[entity] = 1.5f
    }
}