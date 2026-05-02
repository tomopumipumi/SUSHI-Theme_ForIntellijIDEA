package com.tomopumipumi.sushi.effect

object EffectConstants {
    const val MAX_PARTICLES = 2000
    const val BASE_FRAME_TIME_MS = 30.0f
    const val MASK_NONE = 0
    const val MASK_TRANSFORM = 1 shl 0
    const val MASK_PHYSICS = 1 shl 1
    const val MASK_RENDER = 1 shl 2
    const val MASK_LIFECYCLE = 1 shl 3

    const val DEFAULT_PARTICLE_MASK = MASK_TRANSFORM or MASK_PHYSICS or MASK_RENDER or MASK_LIFECYCLE
}

