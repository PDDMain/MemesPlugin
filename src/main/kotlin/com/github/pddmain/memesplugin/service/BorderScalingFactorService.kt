package com.github.pddmain.memesplugin.service

import com.github.pddmain.memesplugin.components.utils.MouseEnteringBorder
import java.awt.event.MouseEvent

abstract class BorderScalingFactorService : ScalingFactorService() {
    protected var mouseEnteringBorder = MouseEnteringBorder.NONE

    override fun mouseExited(e: MouseEvent) {
        mouseEnteringBorder = MouseEnteringBorder.NONE
    }

    override fun mouseEntered(e: MouseEvent) {
        mouseEnteringBorder = closestComponentBorder(
            topDifference = e.y,
            leftDifference = e.x,
            bottomDifference = e.component.height - e.y,
            rightDifference = e.component.width - e.x,
        )
    }

    private fun closestComponentBorder(
        topDifference: Int,
        leftDifference: Int,
        bottomDifference: Int,
        rightDifference: Int,
    ) = when (minOf(topDifference, leftDifference, bottomDifference, rightDifference)) {
        topDifference -> MouseEnteringBorder.TOP
        leftDifference -> MouseEnteringBorder.LEFT
        bottomDifference -> MouseEnteringBorder.BOTTOM
        rightDifference -> MouseEnteringBorder.RIGHT
        else -> MouseEnteringBorder.NONE
    }
}