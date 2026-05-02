package com.tomopumipumi.sushi.effect.ecs.component

import com.tomopumipumi.sushi.effect.EffectConstants.MAX_PARTICLES
import com.tomopumipumi.sushi.effect.ecs.ComponentPool

class TransformComponent : ComponentPool {
    val x = FloatArray(MAX_PARTICLES)
    val y = FloatArray(MAX_PARTICLES)
    val rotation = FloatArray(MAX_PARTICLES)

    override fun copyEntity(from: Int, to: Int) {
        x[to] = x[from]
        y[to] = y[from]
        rotation[to] = rotation[from]
    }
}