package com.github.pddmain.memesplugin.components.wrapper

import com.github.pddmain.memesplugin.service.ScalingFactorService
import java.awt.Image
import java.awt.Point
import java.awt.event.MouseEvent
import javax.swing.ImageIcon
import javax.swing.JLabel

class ImageResizableMovingComponentWrapper(
    private val originalIcon: ImageIcon,
    private val scalingFactorService: ScalingFactorService,
) : MovingComponentWrapper<JLabel>(JLabel(originalIcon)) {
    override fun mouseMoved(e: MouseEvent) {
        component.resize(e.point, scalingFactorService.scalingFactorAfterMouseMoved(e))
    }

    override fun mouseEntered(e: MouseEvent) {
        super.mouseEntered(e)
        scalingFactorService.mouseEntered(e)
    }

    override fun mouseExited(e: MouseEvent) {
        super.mouseExited(e)
        scalingFactorService.mouseExited(e)
    }

    private fun JLabel.resize(mousePoint: Point, scalingFactor: Double) {
        updateIconSize(scalingFactor)
        setBounds(
            mousePoint.x - icon.iconWidth / 2,
            mousePoint.y - icon.iconHeight / 2,
            icon.iconWidth,
            icon.iconHeight
        )
    }

    private fun JLabel.updateIconSize(scalingFactor: Double) {
        icon = ImageIcon(
            originalIcon.image.getScaledInstance(
                calculateWidth(scalingFactor),
                calculateHeight(scalingFactor),
                Image.SCALE_FAST,
            )
        )
    }

    private fun calculateWidth(scalingFactor: Double) = (scalingFactor * originalIcon.iconWidth).toInt()
    private fun calculateHeight(scalingFactor: Double) = (scalingFactor * originalIcon.iconHeight).toInt()
}