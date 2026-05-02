package com.tomopumipumi.sushi.effect.ecs.component

import com.tomopumipumi.sushi.effect.EffectConstants.MAX_PARTICLES
import com.tomopumipumi.sushi.effect.ecs.ComponentPool

class PhysicsComponent : ComponentPool {
    val vx = FloatArray(MAX_PARTICLES)
    val vy = FloatArray(MAX_PARTICLES)
    val gravity = FloatArray(MAX_PARTICLES)
    val friction = FloatArray(MAX_PARTICLES)
    val rotationFactor = FloatArray(MAX_PARTICLES)

    override fun copyEntity(from: Int, to: Int) {
        vx[to] = vx[from]
        vy[to] = vy[from]
        gravity[to] = gravity[from]
        friction[to] = friction[from]
        rotationFactor[to] = rotationFactor[from]
    }
}