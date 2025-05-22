package com.st10371629.flappybirdgame

import android.graphics.Canvas

class GameThread<SurfaceHolder>(
    private val surfaceHolder: SurfaceHolder,
    private val gameView: BirdGameView
) : Thread() {

    var running = false
    var frameCount = 0

    override fun run() {
        var canvas: Canvas?

        while (running) {
            canvas = null
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.update()
                    if (canvas != null) {
                        gameView.draw(canvas)
                    }
                }
            } finally {
                canvas?.let {
                    surfaceHolder.unlockCanvasAndPost(it)
                }
            }

            frameCount++
            sleep(16)
        }
    }
}
