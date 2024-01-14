package com.example.snooziroo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (context) {
            is MainActivity -> {
                drawSnoozie(canvas, (width / 2).toFloat(), (height / 2).toFloat(), (width / 3).toFloat(), Color.RED, 5)
            }

            is ExplainActivity -> {
                drawSnoozie(canvas, (width / 2).toFloat(), (height / 2).toFloat(), (width / 4).toFloat(), Color.BLUE, 5)
            }

            else -> {

            }
        }
    }

    private fun drawSnoozie(canvas: Canvas, xPos: Float, yPos: Float, size: Float, colour: Int, healthState: Int) {
        paint.color = colour
        canvas.drawCircle(xPos + (size / 1.5f), yPos + (size / 3f), size / 1.75f, paint)
        canvas.drawCircle(xPos - (size / 2), yPos + size - (size / 6), size / 3, paint)
        canvas.drawCircle(xPos + (size / 4), yPos + size - (size / 6), size / 3, paint)
        canvas.drawCircle(xPos, yPos, size, paint)
        paint.color = Color.BLACK
    }
}