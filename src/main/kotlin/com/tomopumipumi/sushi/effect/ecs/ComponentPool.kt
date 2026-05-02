package com.tomopumipumi.sushi.effect.ecs

interface ComponentPool {
    fun copyEntity(from: Int, to: Int)
}