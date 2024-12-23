package com.github.pddmain.memesplugin.action

import com.github.pddmain.memesplugin.MyBundle
import com.github.pddmain.memesplugin.components.MemeJFrame
import com.github.pddmain.memesplugin.components.wrapper.ImageResizableMovingComponentWrapper
import com.github.pddmain.memesplugin.service.ScalingFactorService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.ImageIcon

class CreateMemeWindowAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        MemeJFrame(
            ImageResizableMovingComponentWrapper(
                originalIcon = ImageIcon(this::class.java.getResource(MyBundle.message("memeImagePath"))),
                scalingFactorService = (e.dataContext.getData("service") ?: throw IllegalStateException("ScalingFactorService could not be retrieved."))
                        as ScalingFactorService,
            )
        ).apply { isVisible = true }
    }
}
