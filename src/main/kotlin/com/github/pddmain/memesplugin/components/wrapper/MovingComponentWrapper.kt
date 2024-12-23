package com.github.pddmain.memesplugin.components.wrapper

import java.awt.Component
import java.awt.event.MouseEvent

abstract class MovingComponentWrapper<T : Component>(val component: T) {
    init {
        component.isVisible = false
    }

    abstract fun mouseMoved(e : MouseEvent)

    open fun mouseEntered(e : MouseEvent) {
        component.isVisible = true
    }

    open fun mouseExited(e : MouseEvent) {
        component.isVisible = false
    }
}
