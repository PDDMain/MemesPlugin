package com.github.pddmain.memesplugin.components

import com.github.pddmain.memesplugin.MyBundle
import com.github.pddmain.memesplugin.components.wrapper.MovingComponentWrapper
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JFrame

class MemeJFrame<T : Component>(val movingComponentWrapper: MovingComponentWrapper<T>) : JFrame(MyBundle.message("memeWindowTitle")) {
    init {
        setSize(400, 300)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        layout = null
        addMouseListeners()
        add(movingComponentWrapper.component)
    }

    private fun addMouseListeners() {
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                movingComponentWrapper.mouseMoved(e)
            }
        })

        addMouseListener(object : MouseAdapter() {
            override fun mouseExited(e: MouseEvent) {
                movingComponentWrapper.mouseExited(e)
            }

            override fun mouseEntered(e: MouseEvent) {
                movingComponentWrapper.mouseEntered(e)
            }
        })
    }
}