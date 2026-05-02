package com.tomopumipumi.sushi.effect.ecs.system

import com.tomopumipumi.sushi.effect.EffectConstants.MASK_PHYSICS
import com.tomopumipumi.sushi.effect.EffectConstants.MASK_TRANSFORM
import com.tomopumipumi.sushi.effect.ecs.Registry
import kotlin.math.abs
import kotlin.math.pow

class PhysicsSystem {
    private val requiredMask = MASK_TRANSFORM or MASK_PHYSICS

    fun update(registry: Registry, dt: Float, bounceTopDistance: Float) {
        val p = registry.physics
        val t = registry.transform

        for (i in 0 until registry.activeCount) {
            if ((registry.entityMasks[i] and requiredMask) != requiredMask) continue

            p.vy[i] += p.gravity[i] * dt

            val frictionFactor = p.friction[i].pow(dt)
            p.vx[i] *= frictionFactor
            p.vy[i] *= frictionFactor

            t.x[i] += p.vx[i] * dt
            t.y[i] += p.vy[i] * dt

            if (bounceTopDistance > 0f) {
                val topLimit = -abs(bounceTopDistance)
                if (t.y[i] < topLimit) {
                    t.y[i] = topLimit
                    p.vy[i] *= -0.7f
                }
            }

            t.rotation[i] += p.vx[i] * p.rotationFactor[i] * dt
        }
    }
}