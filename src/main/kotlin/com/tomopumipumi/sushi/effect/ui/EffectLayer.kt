package com.tomopumipumi.sushi.effect.ui

import com.intellij.openapi.editor.Editor
import com.intellij.util.ui.GraphicsUtil
import com.tomopumipumi.sushi.effect.ecs.World
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JComponent

class EffectLayer(
    private val editor: Editor,
    private val world: World
) : JComponent() {

    init {
        isOpaque = false
        isFocusable = false
    }

    var repaintAction: (() -> Unit)? = null


    override fun contains(x: Int, y: Int): Boolean {
        return false
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2d = g.create() as? Graphics2D ?: return
        try {
            GraphicsUtil.setupAAPainting(g2d)
            world.renderSystem.update(world.registry, g2d, editor)
        } finally {
            g2d.dispose()
        }
    }

    companion object {
        fun install(editor: Editor, world: World): EffectLayer {
            val layer = EffectLayer(editor, world)
            val contentComponent = editor.contentComponent

            layer.bounds = java.awt.Rectangle(0, 0, contentComponent.width, contentComponent.height)
            contentComponent.add(layer)

            contentComponent.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent) {
                    layer.bounds = java.awt.Rectangle(0, 0, contentComponent.width, contentComponent.height)
                }
            })

            val action = {
                if (layer.isShowing)
                    layer.repaint()
            }
            layer.repaintAction = action
            world.repaintListeners.add(action)

            return layer
        }

        fun uninstall(editor: Editor, layer: EffectLayer, world: World) {
            layer.repaintAction?.let { world.repaintListeners.remove(it) }
            editor.contentComponent.remove(layer)
            editor.contentComponent.repaint()
        }
    }
}