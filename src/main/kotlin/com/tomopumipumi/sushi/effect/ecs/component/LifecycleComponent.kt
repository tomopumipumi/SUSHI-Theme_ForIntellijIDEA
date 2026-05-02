package com.tomopumipumi.sushi.effect.ecs.component

import com.tomopumipumi.sushi.effect.EffectConstants.MAX_PARTICLES
import com.tomopumipumi.sushi.effect.ecs.ComponentPool

class LifecycleComponent : ComponentPool {
    val life = FloatArray(MAX_PARTICLES)
    val maxLife = FloatArray(MAX_PARTICLES)

    override fun copyEntity(from: Int, to: Int) {
        life[to] = life[from]
        maxLife[to] = maxLife[from]
    }
}
