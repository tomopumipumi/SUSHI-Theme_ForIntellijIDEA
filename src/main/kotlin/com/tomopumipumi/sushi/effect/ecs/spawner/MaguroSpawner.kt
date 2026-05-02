package com.tomopumipumi.sushi.effect.ecs.spawner

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiIcons
import com.tomopumipumi.sushi.effect.ecs.Registry
import java.awt.Point
import kotlin.random.Random

private val MAGURO_GRAPHICS = arrayOf(
    GraphicLevel(10, 16, SushiIcons.MAGURO_LV1),
    GraphicLevel(16, 16, SushiIcons.MAGURO_LV2),
    GraphicLevel(20, 14, SushiIcons.MAGURO_LV3),
    GraphicLevel(20, 16, SushiIcons.MAGURO_LV4),
    GraphicLevel(24, 18, SushiIcons.MAGURO_LV5)
)

fun spawnMaguro(
    registry: Registry,
    editor: Editor,
    position: Point,
    level: Int,
    speedMultiplier: Float = 1.0f
) {
    val graphic = getGraphicData(MAGURO_GRAPHICS, level)
    val count = Random.nextInt(2, 4)

    val p = registry.physics
    val l = registry.lifecycle

    repeat(count) {
        val entity = registry.createBaseParticle(editor, position, graphic.icon, graphic.width, graphic.height)
        if (entity == -1) return@repeat

        p.vx[entity] = (Random.nextFloat() - 0.5f) * 15f * speedMultiplier
        p.vy[entity] = -(Random.nextFloat() * 10f + 5f) * speedMultiplier

        l.life[entity] = 20f
        l.maxLife[entity] = 20f

        p.gravity[entity] = 1.0f
        p.friction[entity] = 1.0f
        p.rotationFactor[entity] = 1.0f
    }
}