package com.tomopumipumi.sushi.effect.ecs.spawner

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiIcons
import com.tomopumipumi.sushi.SushiSettings
import com.tomopumipumi.sushi.effect.ecs.Registry
import java.awt.Point
import kotlin.random.Random

private val FEVER_GRAPHICS = arrayOf(
    GraphicLevel(34, 28, SushiIcons.FEVER_LV1),
    GraphicLevel(34, 34, SushiIcons.FEVER_LV2),
    GraphicLevel(36, 28, SushiIcons.FEVER_LV3),
    GraphicLevel(34, 28, SushiIcons.FEVER_LV4),
    GraphicLevel(34, 30, SushiIcons.FEVER_LV5)
)

fun spawnFever(
    registry: Registry,
    editor: Editor,
    position: Point,
    speedMultiplier: Float = 1.0f
) {
    val baseCount = SushiSettings.instance.state.feverSpawnCount
    val count = Random.nextInt(baseCount, baseCount + 4)

    val p = registry.physics
    val l = registry.lifecycle

    repeat(count) {
        val randomGraphicLevel = FEVER_GRAPHICS.random()

        val entity = registry.createBaseParticle(
            editor = editor,
            position = position,
            icon = randomGraphicLevel.icon,
            width = randomGraphicLevel.width,
            height = randomGraphicLevel.height
        )

        if (entity == -1) return@repeat

        p.vx[entity] = (Random.nextFloat() - 0.5f) * 30f * speedMultiplier
        p.vy[entity] = -(Random.nextFloat() * 20f + 10f) * speedMultiplier

        p.gravity[entity] = 1.5f
        p.friction[entity] = 0.95f
        p.rotationFactor[entity] = 1.5f

        l.life[entity] = 35f
        l.maxLife[entity] = 35f
    }
}