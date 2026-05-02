package com.tomopumipumi.sushi.effect.ecs.spawner

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiIcons
import com.tomopumipumi.sushi.effect.ecs.Registry
import java.awt.Point
import kotlin.random.Random

private val IKURA_GRAPHICS = arrayOf(
    GraphicLevel(10, 10, SushiIcons.IKURA_LV1),
    GraphicLevel(16, 16, SushiIcons.IKURA_LV2),
    GraphicLevel(20, 16, SushiIcons.IKURA_LV3),
    GraphicLevel(24, 20, SushiIcons.IKURA_LV4),
    GraphicLevel(24, 24, SushiIcons.IKURA_LV5)
)

fun spawnIkura(
    registry: Registry,
    editor: Editor,
    position: Point,
    level: Int,
    speedMultiplier: Float = 1.0f
) {
    val graphic = getGraphicData(IKURA_GRAPHICS, level)
    val count = Random.nextInt(4, 7)

    val p = registry.physics
    val l = registry.lifecycle

    repeat(count) {
        val entity = registry.createBaseParticle(editor, position, graphic.icon, graphic.width, graphic.height)
        if (entity == -1) return@repeat

        p.vx[entity] = (Random.nextFloat() - 0.5f) * 20f * speedMultiplier
        p.vy[entity] = (Random.nextFloat() - 0.7f) * 15f * speedMultiplier

        l.life[entity] = 30f
        l.maxLife[entity] = 30f

        p.gravity[entity] = 1.0f
        p.friction[entity] = 0.95f
        p.rotationFactor[entity] = 0.5f
    }
}