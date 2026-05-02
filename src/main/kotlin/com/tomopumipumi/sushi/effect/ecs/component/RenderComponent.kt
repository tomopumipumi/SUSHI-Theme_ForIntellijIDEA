package com.tomopumipumi.sushi.effect.ecs.component

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.effect.EffectConstants.MAX_PARTICLES
import com.tomopumipumi.sushi.effect.ecs.ComponentPool
import java.awt.Point
import javax.swing.Icon

class RenderComponent : ComponentPool {
    val editors = arrayOfNulls<Editor>(MAX_PARTICLES)

    val spawnPoints = arrayOfNulls<Point>(MAX_PARTICLES)

    val icons = arrayOfNulls<Icon>(MAX_PARTICLES)

    val width = IntArray(MAX_PARTICLES)
    val height = IntArray(MAX_PARTICLES)

    override fun copyEntity(from: Int, to: Int) {
        editors[to] = editors[from]
        spawnPoints[to] = spawnPoints[from]
        icons[to] = icons[from]
        width[to] = width[from]
        height[to] = height[from]
    }
}