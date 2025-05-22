package com.st10371629.flappybirdgame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class BirdGameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private val thread: GameThread<Any?>
    private lateinit var bird: Bird
    private val towers = mutableListOf<Tower>()
    private var score = 0
    private var startTime: Long = 0
    private val textPaint = Paint()

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        isFocusable = true

        textPaint.color = Color.BLACK
        textPaint.textSize = 60f
        textPaint.isAntiAlias = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        bird = Bird(width, height)
        towers.clear()
        score = 0
        startTime = System.currentTimeMillis()
        thread.running = true
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread.running = false
        while (retry) {
            try {
                thread.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    fun update() {
        bird.update()

        if (thread.frameCount % 150 == 0) {
            towers.add(Tower(width, height))
        }

        val iterator = towers.iterator()
        while (iterator.hasNext()) {
            val tower = iterator.next()
            tower.update()

            if (tower.isOffScreen()) {
                iterator.remove()
                score++
            }

            if (tower.collidesWith(bird)) {
                thread.running = false // Game Over
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.CYAN)
        bird.draw(canvas)

        towers.forEach { it.draw(canvas) }

        val elapsed = (System.currentTimeMillis() - startTime) / 1000
        canvas.drawText("Score: $score", 50f, 100f, textPaint)
        canvas.drawText("Time: ${elapsed}s", 50f, 180f, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            bird.flap()
        }
        return true
    }
}
