package com.tomopumipumi.sushi.effect.manager

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiSettings
import com.tomopumipumi.sushi.effect.EffectTypes
import com.tomopumipumi.sushi.effect.ecs.World
import java.awt.Point
import kotlin.math.max
import kotlin.math.min

class EffectManager(private val world: World) {
    val feverManager = FeverManager()
    private var activeRandomType: String = EffectTypes.MAGURO

    private val maxEffectLevel = 5


    fun trigger(combo: Int, position: Point, editor: Editor) {
        val state = SushiSettings.instance.state
        val effectType = state.effectType

        if (effectType == EffectTypes.NONE) return

        val comboUnit = max(1, state.comboUnit)
        val rawLevel = (combo / comboUnit) + 1
        val safeLevel = min(rawLevel, maxEffectLevel)

        val feverTriggerCombo = max(1, state.feverTriggerCombo)

        if (combo >= feverTriggerCombo && !feverManager.isFever)
            feverManager.start()


        if (feverManager.isFever) {
            world.spawn(EffectTypes.FEVER, editor, position, safeLevel)
        } else {
            var targetType = effectType

            if (targetType == EffectTypes.RANDOM) {
                if (combo == 1) {
                    activeRandomType = EffectTypes.RANDOM_POOL.random()
                }
                targetType = activeRandomType
            }

            world.spawn(targetType, editor, position, safeLevel)
        }
    }

    fun clearParticlesForEditor(editor: Editor) {
        world.killParticlesByEditor(editor)
    }

    fun dispose() {
        feverManager.dispose()
    }
}