package com.github.pddmain.memesplugin.components

import com.github.pddmain.memesplugin.components.wrapper.MovingComponentWrapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.Component
import java.awt.event.MouseEvent
import javax.swing.JFrame

class MemeJFrameTest {
    private lateinit var mockComponent: Component
    private lateinit var mockWrapper: MovingComponentWrapper<Component>
    private lateinit var frame: MemeJFrame<Component>

    @BeforeEach
    fun setUp() {
        mockComponent = mockk(relaxed = true)
        mockWrapper = mockk(relaxed = true) {
            every { component } returns mockComponent
        }
        frame = MemeJFrame(mockWrapper)
    }

    @Test
    fun `test frame initialization`() {
        assertEquals(400, frame.width, "Frame width should be 400.")
        assertEquals(300, frame.height, "Frame height should be 300.")
        assertEquals(JFrame.DISPOSE_ON_CLOSE, frame.defaultCloseOperation, "Default close operation should be DISPOSE_ON_CLOSE.")
        assertEquals(1, frame.contentPane.componentCount, "Frame should contain one component.")
        assertEquals(mockComponent, frame.contentPane.getComponent(0), "The added component should be the mock component.")
    }

    @Test
    fun `test mouse motion listener calls mouseMoved`() {
        val mockEvent = mockk<MouseEvent>(relaxed = true)
        frame.mouseMotionListeners.forEach { it.mouseMoved(mockEvent) }

        verify { mockWrapper.mouseMoved(mockEvent) }
    }

    @Test
    fun `test mouse listener calls mouseEntered and mouseExited`() {
        val mockEvent = mockk<MouseEvent>(relaxed = true)

        frame.mouseListeners.forEach { it.mouseEntered(mockEvent) }
        verify { mockWrapper.mouseEntered(mockEvent) }

        frame.mouseListeners.forEach { it.mouseExited(mockEvent) }
        verify { mockWrapper.mouseExited(mockEvent) }
    }
}
