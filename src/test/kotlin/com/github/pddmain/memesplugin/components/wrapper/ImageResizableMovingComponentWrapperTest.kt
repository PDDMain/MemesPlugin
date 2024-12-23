package com.github.pddmain.memesplugin.components.wrapper

import com.github.pddmain.memesplugin.service.RelativeScalingFactorServiceTest.Companion.mouseEvent
import com.github.pddmain.memesplugin.service.ScalingFactorService
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Image
import java.awt.event.MouseEvent
import java.util.stream.Stream
import javax.swing.ImageIcon
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ImageResizableMovingComponentWrapperTest(
    @RelaxedMockK private val scalingFactorService: ScalingFactorService,
    @RelaxedMockK private val originalIcon: ImageIcon,
) {
    private lateinit var wrapper: ImageResizableMovingComponentWrapper

    @BeforeEach
    fun setUp() {
        wrapper = ImageResizableMovingComponentWrapper(originalIcon, scalingFactorService)
    }

    @ParameterizedTest
    @MethodSource("mouseMovesCases")
    fun `test mouseMoved resizes and repositions component`(
        mouseX: Int,
        mouseY: Int,
        iconWidth: Int,
        iconHeight: Int,
        scalingFactor: Double,
        expectedIconX: Int,
        expectedIconY: Int,
        expectedIconWidth: Int,
        expectedIconHeight: Int,
    ) {
        val mockEvent = mouseEvent(mockk(relaxed = true), mouseX, mouseY)
        every { scalingFactorService.scalingFactorAfterMouseMoved(mockEvent) } returns scalingFactor
        every { originalIcon.iconWidth } returns iconWidth
        every { originalIcon.iconHeight } returns iconHeight

        val width = slot<Int>()
        val height = slot<Int>()
        every { originalIcon.image.getScaledInstance(capture(width), capture(height), Image.SCALE_FAST) } returns
                mockk(relaxed = true)

        mockkConstructor(ImageIcon::class)
        every { constructedWith<ImageIcon>(OfTypeMatcher<Image>(Image::class)).iconWidth } returns expectedIconWidth
        every { constructedWith<ImageIcon>(OfTypeMatcher<Image>(Image::class)).iconHeight } returns expectedIconHeight

        wrapper.mouseMoved(mockEvent)

        verify { originalIcon.image.getScaledInstance(expectedIconWidth, expectedIconHeight, Image.SCALE_FAST) }
        val component = wrapper.component
        assertEquals(component.x, expectedIconX)
        assertEquals(component.y, expectedIconY)
        assertEquals(component.width, expectedIconWidth)
        assertEquals(component.height, expectedIconHeight)
        assertEquals(width.captured, expectedIconWidth)
        assertEquals(height.captured, expectedIconHeight)
    }

    @Test
    fun `test mouseEntered delegates to scalingFactorService`() {
        val mockEvent = mockk<MouseEvent>(relaxed = true)
        wrapper.mouseEntered(mockEvent)
        verify { scalingFactorService.mouseEntered(mockEvent) }
    }

    @Test
    fun `test mouseExited delegates to scalingFactorService`() {
        val mockEvent = mockk<MouseEvent>(relaxed = true)
        wrapper.mouseExited(mockEvent)
        verify { scalingFactorService.mouseExited(mockEvent) }
    }

    companion object {
        @JvmStatic
        fun mouseMovesCases(): Stream<Arguments> = Stream.of(
            Arguments.of(160, 100, 120, 75, 1.0, 100, 63, 120, 75),
            Arguments.of(160, 100, 120, 75, 0.75, 115, 72, 90, 56),
        )
    }
}