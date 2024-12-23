package com.github.pddmain.memesplugin.service

import com.github.pddmain.memesplugin.components.utils.MouseEnteringBorder
import java.awt.event.MouseEvent

class RelativeScalingFactorService(private val endOfResizing : Double = 0.5) : BorderScalingFactorService() {
    override fun scalingFactorAfterMouseMoved(e: MouseEvent) : Double =
        relativeValue(
            percentage = borderProximity(
                x = e.x,
                y = e.y,
                width = e.component.width,
                height = e.component.height,
            ) / endOfResizing
        )

    private fun relativeValue(
        begin: Double = MIN_SCALING_FACTOR,
        end: Double = MAX_SCALING_FACTOR,
        percentage: Double
    ) = begin + (end - begin) * percentage.coerceIn(0.0, 1.0)

    private fun borderProximity(x: Int, y: Int, width: Int, height: Int) =
        when (mouseEnteringBorder) {
            MouseEnteringBorder.TOP -> y.toDouble() / height
            MouseEnteringBorder.LEFT -> x.toDouble() / width
            MouseEnteringBorder.BOTTOM -> (height - y).toDouble() / height
            MouseEnteringBorder.RIGHT -> (width - x).toDouble() / width
            else -> throw IllegalArgumentException("Unexpected mouse entering border value: $mouseEnteringBorder")
        }

    companion object {
        private const val MIN_SCALING_FACTOR = 0.25
        private const val MAX_SCALING_FACTOR = 1.0
    }
}
