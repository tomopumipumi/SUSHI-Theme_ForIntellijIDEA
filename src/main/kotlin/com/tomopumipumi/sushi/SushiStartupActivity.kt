package com.tomopumipumi.sushi

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.tomopumipumi.sushi.effect.SushiEffectController
import com.tomopumipumi.sushi.progressbar.SushiProgressBarUi
import javax.swing.UIManager

class SushiStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        if (SushiSettings.instance.state.enableProgressBar) {
            UIManager.put("ProgressBarUI", SushiProgressBarUi::class.java.name)
            UIManager.getDefaults()[SushiProgressBarUi::class.java.name] = SushiProgressBarUi::class.java
        }

        project.service<SushiEffectController>()
    }
}