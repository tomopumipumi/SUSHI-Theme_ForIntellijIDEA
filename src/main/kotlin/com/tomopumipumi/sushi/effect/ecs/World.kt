package com.tomopumipumi.sushi.effect.ecs

import com.intellij.openapi.editor.Editor
import com.tomopumipumi.sushi.SushiSettings
import com.tomopumipumi.sushi.effect.EffectConstants
import com.tomopumipumi.sushi.effect.EffectTypes
import com.tomopumipumi.sushi.effect.ecs.spawner.*
import com.tomopumipumi.sushi.effect.ecs.system.*
import java.awt.Point
import java.util.concurrent.CopyOnWriteArrayList
import javax.swing.Timer

class World {
    val registry = Registry()

    private val physicsSystem = PhysicsSystem()
    private val lifecycleSystem = LifecycleSystem()
    val renderSystem = RenderSystem()

    private var swingTimer: Timer? = null
    private var lastTimeNano: Long = 0L

    val repaintListeners = CopyOnWriteArrayList<() -> Unit>()

    private fun startLoop() {
        if (swingTimer?.isRunning == true) return

        val fps = SushiSettings.instance.state.fps
        val delay = 1000 / fps

        lastTimeNano = System.nanoTime()

        swingTimer = Timer(delay) {
            update()
        }.apply { start() }
    }

    private fun stopLoop() {
        swingTimer?.stop()
        swingTimer = null
    }

    private fun update() {
        if (registry.activeCount == 0) {
            stopLoop()
            notifyRepaint()
            return
        }

        val now = System.nanoTime()
        val baseFrameTimeNano = EffectConstants.BASE_FRAME_TIME_MS * 1_000_000f
        var dt = (now - lastTimeNano) / baseFrameTimeNano
        lastTimeNano = now

        if (dt > 3.0f) dt = 3.0f

        val bounceDistance = SushiSettings.instance.state.bounceTopDistance.toFloat()

        physicsSystem.update(registry, dt, bounceDistance)
        lifecycleSystem.update(registry, dt)

        notifyRepaint()
    }

    private fun notifyRepaint() {
        repaintListeners.forEach { it.invoke() }
    }

    fun spawn(type: String, editor: Editor, position: Point, level: Int) {
        val speedMultiplier = SushiSettings.instance.state.particleSpeedMultiplier

        when (type) {
            EffectTypes.MAGURO -> spawnMaguro(registry, editor, position, level, speedMultiplier)
            EffectTypes.IKURA -> spawnIkura(registry, editor, position, level, speedMultiplier)
            EffectTypes.EBI -> spawnEbi(registry, editor, position, level, speedMultiplier)
            EffectTypes.MATCHA -> spawnMatcha(registry, editor, position, level, speedMultiplier)
            EffectTypes.FEVER -> spawnFever(registry, editor, position, speedMultiplier)
        }

        startLoop()
    }

    fun killParticlesByEditor(closedEditor: Editor) {
        val targetIndices = registry.getEntitiesForEditor(closedEditor)
        for (i in targetIndices)
            registry.lifecycle.life[i] = 0f
    }

    fun dispose() {
        stopLoop()
        repaintListeners.clear()
    }
}