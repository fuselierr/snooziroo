package com.example.snooziroo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class EllipseView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var outlineColor: Int = Color.BLACK
    private var fillColor: Int = Color.TRANSPARENT

    private val ellipsePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init{
        ellipsePaint.style = Paint.Style.FILL
        ellipsePaint.color = fillColor

        outlinePaint.style = Paint.Style.STROKE
        outlinePaint.color = outlineColor
    }

    fun setColors(outlineColor: Int, fillColor: Int) {
        this.outlineColor = outlineColor
        this.fillColor = fillColor

        outlinePaint.color = outlineColor
        ellipsePaint.color = fillColor

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Get the center coordinates of the view
        val centerX = width / 2f
        val centerY = height / 2f

        // Calculate the horizontal and vertical radii as half of the respective dimensions (width or height)
        val horizontalRadius = width / 2f
        val verticalRadius = height / 2f

        // Draw the ellipse with the specified outline and fill colors
        canvas.drawOval(centerX - horizontalRadius, centerY - verticalRadius,
            centerX + horizontalRadius, centerY + verticalRadius, ellipsePaint)
        canvas.drawOval(centerX - horizontalRadius, centerY - verticalRadius,
            centerX + horizontalRadius, centerY + verticalRadius, outlinePaint)
    }

}