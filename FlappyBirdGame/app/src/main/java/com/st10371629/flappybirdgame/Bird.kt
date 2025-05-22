package com.st10371629.flappybirdgame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Bird(screenWidth: Int, screenHeight: Int) {
    private val radius = 50f
    private val x = screenWidth / 4f
    private var y = screenHeight / 2f
    private var velocity = 0f
    private val gravity = 0.6f

    fun update() {
        velocity += gravity
        y += velocity
    }

    fun draw(canvas: Canvas) {
        val paint = Paint().apply { color = Color.YELLOW }
        canvas.drawCircle(x, y, radius, paint)
    }

    fun getBounds(): RectF {
        return RectF(x - radius, y - radius, x + radius, y + radius)
    }

    fun flap() {
        TODO("Not yet implemented")
    }
}
