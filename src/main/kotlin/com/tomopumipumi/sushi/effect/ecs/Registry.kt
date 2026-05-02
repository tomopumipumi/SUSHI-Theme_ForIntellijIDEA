package com.tomopumipumi.sushi.effect.ecs

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.effect.EffectConstants.MAX_PARTICLES
import com.tomopumipumi.sushi.effect.EffectConstants.MASK_NONE
import com.tomopumipumi.sushi.effect.ecs.component.*

class Registry {
    var activeCount: Int = 0
    val entityMasks = IntArray(MAX_PARTICLES)

    private val editorEntities = mutableMapOf<Editor, MutableList<Int>>()

    val transform = TransformComponent()
    val physics = PhysicsComponent()
    val render = RenderComponent()
    val lifecycle = LifecycleComponent()

    private val componentPools = arrayOf(transform, physics, render, lifecycle)

    fun getEntitiesForEditor(editor: Editor): List<Int> = editorEntities[editor] ?: emptyList()

    fun removeEditor(editor: Editor) {
        editorEntities.remove(editor)
    }

    fun createEntity(mask: Int, editor: Editor?): Int {
        if (activeCount >= MAX_PARTICLES) return -1

        val entity = activeCount
        entityMasks[entity] = mask

        if (editor != null) {
            render.editors[entity] = editor
            editorEntities.getOrPut(editor) { mutableListOf() }.add(entity)
        }

        activeCount++
        return entity
    }

    fun destroyEntity(entity: Int) {
        val last = activeCount - 1
        val targetEditor = render.editors[entity]

        if (targetEditor != null) editorEntities[targetEditor]?.remove(entity)

        if (entity != last) {
            val lastEditor = render.editors[last]
            if (lastEditor != null) {
                val list = editorEntities[lastEditor]
                if (list != null) {
                    val idx = list.indexOf(last)
                    if (idx != -1) list[idx] = entity
                }
            }

            entityMasks[entity] = entityMasks[last]

            for (i in componentPools.indices) componentPools[i].copyEntity(last, entity)
        }

        entityMasks[last] = MASK_NONE
        render.editors[last] = null
        render.spawnPoints[last] = null
        render.icons[last] = null

        activeCount--
    }
}