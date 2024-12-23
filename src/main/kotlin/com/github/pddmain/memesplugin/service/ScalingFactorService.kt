package com.github.pddmain.memesplugin.service

import java.awt.event.MouseEvent

abstract class ScalingFactorService {
    abstract fun scalingFactorAfterMouseMoved(e: MouseEvent): Double
    abstract fun mouseExited(e: MouseEvent)
    abstract fun mouseEntered(e: MouseEvent)
}
