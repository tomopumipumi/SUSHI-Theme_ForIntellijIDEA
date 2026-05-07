package com.tomopumipumi.sushi.effect

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.*
import com.intellij.openapi.editor.markup.HighlighterLayer
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.editor.markup.RangeHighlighter
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.project.Project
import com.intellij.ui.JBColor
import com.tomopumipumi.sushi.effect.ecs.World
import com.tomopumipumi.sushi.effect.manager.ComboManager
import com.tomopumipumi.sushi.effect.manager.EffectManager
import com.tomopumipumi.sushi.effect.ui.EffectLayer
import java.awt.Color

@Service(Service.Level.PROJECT)
class SushiEffectController(private val project: Project) : Disposable {
    private val world = World()
    val effectManager = EffectManager(world)

    private val comboManager = ComboManager { combo, position, editor ->
        effectManager.trigger(combo, position, editor)
    }

    private val caretListener = object : CaretListener {
        override fun caretPositionChanged(event: CaretEvent) {
            if (event.editor.project != project) return
            if (effectManager.feverManager.isFever) {
                updateFeverHighlight(event.editor)
            }
        }
    }

    private val editorFactoryListener = object : EditorFactoryListener {
        override fun editorCreated(event: EditorFactoryEvent) {
            if (event.editor.project != project) return

            val editor = event.editor
            val layer = EffectLayer.install(editor, world)
            effectLayers[editor] = layer
        }

        override fun editorReleased(event: EditorFactoryEvent) {
            if (event.editor.project != project) return

            val editor = event.editor
            effectLayers.remove(editor)?.let { layer ->
                EffectLayer.uninstall(editor, layer, world)
            }
            clearFeverHighlight(editor)
            effectManager.clearParticlesForEditor(editor)
            world.registry.removeEditor(editor)
        }
    }

    private val documentListener = object : DocumentListener {
        override fun documentChanged(event: DocumentEvent) {
            if (event.document.isInBulkUpdate) return

            if (event.newFragment.isEmpty() || event.newLength > 50) return

            val editors = EditorFactory.getInstance().getEditors(event.document)
            if (editors.isEmpty()) return

            val editor = editors.find { it.component.isShowing && it.project == project }
                ?: editors.firstOrNull { it.project == project }
            if (editor == null) return

            try {
                val offset = event.offset + event.newLength

                if (offset < 0 || offset > editor.document.textLength) return

                val visualPos = editor.offsetToVisualPosition(offset)
                val point = editor.visualPositionToXY(visualPos)
                point.translate(0, -editor.lineHeight / 2)
                comboManager.registerKeystroke(editor, point)

            } catch (e: IndexOutOfBoundsException) {
            } catch (e: Exception) {
            }
        }
    }

    private val effectLayers = mutableMapOf<Editor, EffectLayer>()

    private val feverHighlighters = mutableMapOf<Editor, RangeHighlighter>()

    private val feverAttributes = TextAttributes().apply {
        backgroundColor = JBColor(Color(255, 215, 0, 40), Color(255, 215, 0, 40))
    }

    init {
        val multicaster = EditorFactory.getInstance().eventMulticaster

        effectManager.feverManager.onFeverStateChanged { isFever ->
            val editors = EditorFactory.getInstance().allEditors.filter { it.project == project }
            if (isFever) editors.forEach { updateFeverHighlight(it) }
            else editors.forEach { clearFeverHighlight(it) }
        }

        multicaster.addCaretListener(caretListener, this)
        EditorFactory.getInstance().addEditorFactoryListener(editorFactoryListener, this)
        multicaster.addDocumentListener(documentListener, this)
    }


    private fun updateFeverHighlight(editor: Editor) {
        clearFeverHighlight(editor)

        val document = editor.document
        val line = editor.caretModel.logicalPosition.line
        if (line < 0 || line >= document.lineCount) return

        val startOffset = document.getLineStartOffset(line)
        val endOffset = document.getLineEndOffset(line)

        val highlighter = editor.markupModel.addRangeHighlighter(
            startOffset,
            endOffset,
            HighlighterLayer.SELECTION - 1,
            feverAttributes,
            HighlighterTargetArea.LINES_IN_RANGE
        )
        feverHighlighters[editor] = highlighter
    }

    private fun clearFeverHighlight(editor: Editor) {
        feverHighlighters.remove(editor)?.dispose()
    }

    override fun dispose() {
        world.dispose()
        effectManager.dispose()
        comboManager.dispose()

        feverHighlighters.values.forEach { it.dispose() }
        feverHighlighters.clear()
        effectLayers.clear()
    }
}