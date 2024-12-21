package com.github.pddmain.memesplugin.toolWindow

import com.github.pddmain.memesplugin.MyBundle
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.Content
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import com.intellij.ui.content.ContentFactory
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Nested
import javax.swing.JButton
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
class MemeToolWindowFactoryTest(
    @MockK private val project: Project,
    @MockK private val contentFactory: ContentFactory,
    @RelaxedMockK private val toolWindow: ToolWindow,
) {
    @BeforeEach
    fun setUp() {
        mockkStatic(ContentFactory::class)
        every { ContentFactory.getInstance() } returns contentFactory
    }

    @Test
    fun `test createToolWindowContent adds content to toolWindow`() {
        val mockContent = mockk<Content>(relaxed = true)
        every { contentFactory.createContent(any(), any(), any()) } returns mockContent

        val factory = MemeToolWindowFactory()
        factory.createToolWindowContent(project, toolWindow)

        verify { contentFactory.createContent(any(), null, false) }
        verify { toolWindow.contentManager.addContent(mockContent) }
    }

    @Nested
    @ExtendWith(MockKExtension::class)
    inner class MemeToolWindowTest(
        @MockK private val project: Project,
        @MockK private val actionManager: ActionManager,
        @RelaxedMockK private val mockAction: AnAction,
    ) {
        private val myToolWindow = MemeToolWindowFactory.MemeToolWindow(project)

        @BeforeEach
        fun setUp() {
            mockkStatic(ActionManager::class)
            every { ActionManager.getInstance() } returns actionManager
            every { actionManager.getAction(MyBundle.message("action.createMemeWindowAction")) } returns mockAction
        }

        @Test
        fun `test Create Meme Window button triggers action`() {
            val content = myToolWindow.getContent()
            val button = content.components.filterIsInstance<JButton>().find { it.text == "Create Meme Window" }

            assertNotNull(button, "The button 'Create Meme Window' should be present.")

            button.doClick()

            verify { actionManager.getAction(MyBundle.message("action.createMemeWindowAction")) }
            verify { mockAction.actionPerformed(any()) }
        }
    }
}