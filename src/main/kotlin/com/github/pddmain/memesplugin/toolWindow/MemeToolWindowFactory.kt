package com.github.pddmain.memesplugin.toolWindow

import com.github.pddmain.memesplugin.MyBundle
import com.github.pddmain.memesplugin.service.RelativeScalingFactorService
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import javax.swing.JButton

class MemeToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MemeToolWindow()
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    class MemeToolWindow {
        fun getContent() = JBPanel<JBPanel<*>>().apply {
            add(JButton("Create Meme Window").apply {
                addActionListener {
                    val actionManager = ActionManager.getInstance()
                    val action =
                        actionManager.getAction(MyBundle.message("action.createMemeWindowAction"))
                    val dataContext = DataContext { key ->
                        if ("service" == key) RelativeScalingFactorService() else null
                    }
                    action.actionPerformed(
                        AnActionEvent.createFromDataContext(
                            "MyToolWindow", null, dataContext
                        )
                    )
                }
            })
        }
    }
}
