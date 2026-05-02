package com.tomopumipumi.sushi.effect.ecs.spawner

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiIcons
import com.tomopumipumi.sushi.effect.ecs.Registry
import java.awt.Point
import kotlin.random.Random

private val MATCHA_GRAPHICS = arrayOf(
    GraphicLevel(12, 12, SushiIcons.MATCHA_LV1),
    GraphicLevel(16, 16, SushiIcons.MATCHA_LV2),
    GraphicLevel(20, 22, SushiIcons.MATCHA_LV3),
    GraphicLevel(20, 22, SushiIcons.MATCHA_LV4),
    GraphicLevel(20, 28, SushiIcons.MATCHA_LV5)
)

fun spawnMatcha(
    registry: Registry,
    editor: Editor,
    position: Point,
    level: Int,
    speedMultiplier: Float = 1.0f
) {
    val graphic = getGraphicData(MATCHA_GRAPHICS, level)
    val count = Random.nextInt(1, 3)

    val p = registry.physics
    val l = registry.lifecycle

    repeat(count) {
        val entity = registry.createBaseParticle(editor, position, graphic.icon, graphic.width, graphic.height)
        if (entity == -1) return@repeat

        p.vx[entity] = (Random.nextFloat() - 0.5f) * 5f * speedMultiplier
        p.vy[entity] = -(Random.nextFloat() * 3f + 2f) * speedMultiplier

        l.life[entity] = 30f
        l.maxLife[entity] = 30f

        p.gravity[entity] = -0.5f
        p.friction[entity] = 0.9f
        p.rotationFactor[entity] = 1.0f
    }
}