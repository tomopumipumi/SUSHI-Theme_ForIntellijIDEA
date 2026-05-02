package com.tomopumipumi.sushi.effect.ecs.system

import com.tomopumipumi.sushi.effect.EffectConstants.MASK_LIFECYCLE
import com.tomopumipumi.sushi.effect.ecs.Registry

class LifecycleSystem {
    private val requiredMask = MASK_LIFECYCLE

    fun update(registry: Registry, dt: Float) {
        for (i in registry.activeCount - 1 downTo 0) {
            if ((registry.entityMasks[i] and requiredMask) != requiredMask) continue

            registry.lifecycle.life[i] -= dt

            if (registry.lifecycle.life[i] <= 0f) registry.destroyEntity(i)
        }
    }
}