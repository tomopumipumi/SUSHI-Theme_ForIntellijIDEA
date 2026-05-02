package com.tomopumipumi.sushi.effect.ecs.spawner

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.effect.EffectConstants
import com.tomopumipumi.sushi.effect.ecs.Registry
import java.awt.Point
import javax.swing.Icon

fun Registry.createBaseParticle(
    editor: Editor,
    position: Point,
    icon: Icon,
    width: Int,
    height: Int
): Int {
    val entity = this.createEntity(EffectConstants.DEFAULT_PARTICLE_MASK, editor)
    if (entity == -1) return -1

    this.render.spawnPoints[entity] = Point(position.x, position.y)
    this.render.icons[entity] = icon
    this.render.width[entity] = width
    this.render.height[entity] = height

    this.transform.x[entity] = 0f
    this.transform.y[entity] = 0f
    this.transform.rotation[entity] = kotlin.random.Random.nextFloat() * 360f

    return entity
}