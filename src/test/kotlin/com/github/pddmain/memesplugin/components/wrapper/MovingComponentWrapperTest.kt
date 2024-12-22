package com.github.pddmain.memesplugin.components.wrapper

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.Component
import java.awt.event.MouseEvent

class MovingComponentWrapperTest {
    private lateinit var mockComponent: Component
    private lateinit var wrapper: MovingComponentWrapper<Component>

    @BeforeEach
    fun setUp() {
        mockComponent = mockk(relaxed = true)
        wrapper = object : MovingComponentWrapper<Component>(mockComponent) {
            override fun mouseMoved(e: MouseEvent) {
                // No implementation needed for this test
            }
        }
    }

    @Test
    fun `test component is invisible by default`() {
        assertFalse(mockComponent.isVisible, "Component should be invisible by default.")
    }

    @Test
    fun `test mouseEntered makes component visible`() {
        val mockEvent = mockk<MouseEvent>(relaxed = true)

        wrapper.mouseEntered(mockEvent)

        verify { mockComponent.isVisible = true }
    }

    @Test
    fun `test mouseExited makes component invisible`() {
        val mockEvent = mockk<MouseEvent>(relaxed = true)

        wrapper.mouseExited(mockEvent)

        verify { mockComponent.isVisible = false }
    }
}