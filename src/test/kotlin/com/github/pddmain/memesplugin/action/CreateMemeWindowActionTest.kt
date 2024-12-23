package com.github.pddmain.memesplugin.action

import com.github.pddmain.memesplugin.service.ScalingFactorService
import com.intellij.openapi.actionSystem.AnActionEvent
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateMemeWindowActionTest {
    private val action = CreateMemeWindowAction()

    @Test
    fun `call actionPerformed`() {
        val mockKEvent = mockk<AnActionEvent>()
        every { mockKEvent.dataContext } returns mockk {
            every { getData("service") } returns mockk<ScalingFactorService>()
        }
        action.actionPerformed(mockKEvent)
    }

    @Test
    fun `actionPerformed throws exception`() {
        val mockKEvent = mockk<AnActionEvent>()
        every { mockKEvent.dataContext } returns mockk {
            every { getData("service") } returns null
        }
        assertThrows<IllegalStateException> { action.actionPerformed(mockKEvent) }
    }
}