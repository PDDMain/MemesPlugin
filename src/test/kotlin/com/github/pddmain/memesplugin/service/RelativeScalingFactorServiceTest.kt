package com.github.pddmain.memesplugin.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Component
import java.awt.Point
import java.awt.event.MouseEvent
import java.util.stream.Stream
import kotlin.test.assertEquals

class RelativeScalingFactorServiceTest {
    private lateinit var service: RelativeScalingFactorService

    @BeforeEach
    fun setUp() {
        service = RelativeScalingFactorService()
    }

    @ParameterizedTest
    @MethodSource("enterAndStayCases", "enterAndMoveCases")
    fun `check scalingFactorAfterMouseMoved for various positions`(
        enterX: Int,
        enterY: Int,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        expectedScalingFactor: Double,
    ) {
        val mockComponent = mockk<Component> {
            every { this@mockk.width } returns width
            every { this@mockk.height } returns height
            every { this@mockk.locationOnScreen } returns Point(0, 0)
        }

        val enterMouseEvent = mouseEvent(mockComponent, enterX, enterY)
        service.mouseEntered(enterMouseEvent)

        val moveMouseEvent = mouseEvent(mockComponent, x, y)
        assertEquals(expectedScalingFactor, service.scalingFactorAfterMouseMoved(moveMouseEvent))
    }


    companion object {
        internal fun mouseEvent(component: Component, x: Int, y: Int) = MouseEvent(component, 0, 0, 0, x, y, 0, false)

        @JvmStatic
        fun enterAndStayCases(): Stream<Arguments> = Stream.of(
            // Case: Enter from the left border
            Arguments.of(0, 10, 0, 10, 600, 800, 0.25),
            // Case: Enter from the top border
            Arguments.of(10, 0, 10, 0, 600, 800, 0.25),
            // Case: Enter from the right border
            Arguments.of(600, 790, 600, 790, 600, 800, 0.25),
            // Case: Enter from the bottom border
            Arguments.of(10, 800, 10, 800, 600, 800, 0.25),
        )

        @JvmStatic
        fun enterAndMoveCases(): Stream<Arguments> = Stream.of(
            // Case: Enter from the left border and move to the center
            Arguments.of(0, 30, 300, 30, 600, 800, 1.0),
            // Case: Enter from the top border and move to the 1/6
            Arguments.of(50, 0, 50, 100, 200, 600, 0.5),
            // Case: Enter from the top border and move to the 1/3
            Arguments.of(50, 0, 50, 200, 800, 600, 0.75),
            // Case: Enter from the right border and move to the left above center
            Arguments.of(600, 790, 230, 790, 600, 800, 1.0),
            // Case: Enter from the bottom border and move up above center
            Arguments.of(590, 800, 590, 200, 600, 800, 1.0),

            // Case: Enter from left and move to quarter position
            Arguments.of(0, 20, 150, 20, 600, 800, 0.625),
            // Case: Enter from bottom and move slightly upward
            Arguments.of(500, 800, 500, 750, 600, 800, 0.34375),
        )
    }
}