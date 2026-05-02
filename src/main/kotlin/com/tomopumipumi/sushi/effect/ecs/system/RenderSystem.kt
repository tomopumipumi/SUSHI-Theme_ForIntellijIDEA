package com.tomopumipumi.sushi.effect.ecs.system

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.effect.EffectConstants.MASK_LIFECYCLE
import com.tomopumipumi.sushi.effect.EffectConstants.MASK_RENDER
import com.tomopumipumi.sushi.effect.EffectConstants.MASK_TRANSFORM
import com.tomopumipumi.sushi.effect.ecs.Registry
import java.awt.AlphaComposite
import java.awt.Graphics2D

class RenderSystem {
    private val requiredMask = MASK_TRANSFORM or MASK_RENDER or MASK_LIFECYCLE

    private val alphaCache = Array(11) { i ->
        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, i * 0.1f)
    }

    fun update(registry: Registry, g2d: Graphics2D, currentEditor: Editor) {
        val originalTransform = g2d.transform
        val r = registry.render
        val t = registry.transform
        val l = registry.lifecycle

        val targetIndices = registry.getEntitiesForEditor(currentEditor)

        for (i in targetIndices) {
            if ((registry.entityMasks[i] and requiredMask) != requiredMask) continue

            val icon = r.icons[i] ?: continue
            val spawnPoint = r.spawnPoints[i] ?: continue

            val alphaIdx = ((l.life[i] / l.maxLife[i]) * 10).toInt().coerceIn(0, 10)
            g2d.composite = alphaCache[alphaIdx]

            g2d.translate(spawnPoint.x + t.x[i].toDouble(), spawnPoint.y + t.y[i].toDouble())
            g2d.rotate(Math.toRadians(t.rotation[i].toDouble()))

            icon.paintIcon(null, g2d, -r.width[i] / 2, -r.height[i] / 2)

            g2d.transform = originalTransform
        }
    }
}