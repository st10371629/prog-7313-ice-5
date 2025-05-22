package com.st10371629.flappybirdgame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import kotlin.random.Random

class Tower(screenWidth: Int, private val screenHeight: Int) {
    private var x = screenWidth
    private val width = 150
    private val gap = 300
    private val speed = 10
    private val topHeight = Random().nextInt(screenHeight - gap - 200) + 100
    private val bottomY = topHeight + gap

    fun update() {
        x -= speed
    }

    fun draw(canvas: Canvas) {
        val paint = Paint().apply { color = Color.GREEN }
        canvas.drawRect(Rect(x, 0, x + width, topHeight), paint)
        canvas.drawRect(Rect(x, bottomY, x + width, screenHeight), paint)
    }

    fun isOffScreen(): Boolean {
        return x + width < 0
    }

    fun collidesWith(bird: Bird): Boolean {
        val birdRect = bird.getBounds()
        val topRect = Rect(x, 0, x + width, topHeight)
        val bottomRect = Rect(x, bottomY, x + width, screenHeight)
        return RectF.intersects(birdRect, RectF(topRect)) || RectF.intersects(birdRect, RectF(bottomRect))
    }
}
