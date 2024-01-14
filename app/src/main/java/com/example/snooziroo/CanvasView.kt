package com.example.snooziroo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val snoozie = Snoozie.getInstance()

        when (context) {
            is MainActivity -> {
                drawSnoozie(canvas, (width / 2).toFloat(), (height / 2).toFloat(), (width / 3).toFloat(), Color.RED, 5)
            }

            is ExplainActivity -> {
                drawSnoozie(canvas, (width / 2).toFloat(), (height / 2).toFloat(), (width / 4).toFloat(), Color.BLUE, 5)
            }

            is SnoozieSearchActivity -> {
                drawSnoozie(canvas, (width / 2).toFloat(), (height / 3).toFloat(), (width / 4).toFloat(), snoozie.getSnoozieColour(), 5)
            }

            is NameActivity -> {
                drawSnoozie(canvas, (width / 2).toFloat(), (height / 3).toFloat(), (width / 4).toFloat(), snoozie.getSnoozieColour(), 5)
            }

            is FunctionalActivity -> {
                drawSnoozie(canvas, (width / 2).toFloat(), (height / 1.5).toFloat(), (width / 6).toFloat(), snoozie.getSnoozieColour(), 5)
            }

            else -> {

            }
        }
    }

    private fun drawSnoozie(canvas: Canvas, xPos: Float, yPos: Float, size: Float, colour: Int, healthState: Int) {

        val darkerColor = darkenColor(colour, 0.8f)
        paint.color = darkerColor

        canvas.drawCircle(xPos + (size / 1.5f), yPos + (size / 3f), size / 1.75f, paint)
        canvas.drawCircle(xPos - (size / 2), yPos + size - (size / 6), size / 3, paint)
        canvas.drawCircle(xPos + (size / 4), yPos + size - (size / 6), size / 3, paint)
        paint.color = colour
        canvas.drawCircle(xPos, yPos, size, paint)

        drawEllipse(canvas, xPos - (size / 2) + size / 6, yPos, size / 3, size / 6, Color.BLACK)
        drawEllipse(canvas, xPos - (size / 1.25f), yPos, size / 3, size / 6, Color.BLACK)
        drawEllipse(canvas, xPos - (size / 2) + size / 6, yPos, size / 8, size / 8, Color.WHITE)
        drawEllipse(canvas, xPos - (size / 1.25f), yPos, size / 8, size / 8, Color.WHITE)
    }

    private fun drawEllipse(canvas: Canvas, xPos: Float, yPos: Float, height: Float, width: Float, colour: Int) {
        paint.color = colour
        canvas.drawOval(RectF(
            xPos, yPos, xPos + width, yPos + height
        ), paint)
    }

    private fun darkenColor(color: Int, factor: Float): Int {
        val alpha = Color.alpha(color)
        val red = (Color.red(color) * factor).toInt()
        val green = (Color.green(color) * factor).toInt()
        val blue = (Color.blue(color) * factor).toInt()
        return Color.argb(alpha, red, green, blue)
    }
}