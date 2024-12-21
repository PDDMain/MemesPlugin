package com.github.pddmain.memesplugin.action

import com.intellij.openapi.actionSystem.AnActionEvent
import io.mockk.mockk
import org.junit.jupiter.api.Test

class CreateMemeWindowActionTest {
    private val action = CreateMemeWindowAction()

    @Test
    fun `call actionPerformed`() {
        val mockKEvent = mockk<AnActionEvent>()
        action.actionPerformed(mockKEvent)
    }
}