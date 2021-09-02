package com.example.tic_tac_toe_game.custom_view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class TicTacToeView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    //private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint = Paint()

    private val X_PARTITION_RATIO = 1 / 3f
    private val Y_PARTITION_RATIO = 1 / 3f
    private val numberOfRows = 3

    private var cellPressListener: CellPressedListener? = null


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = Math.min(measuredHeight, measuredWidth)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawVerticalLines(canvas)
        drawHorizontalLines(canvas)
    }

    private fun drawVerticalLines(canvas: Canvas) {
        canvas.drawLine(width * X_PARTITION_RATIO, 0f, width * X_PARTITION_RATIO, height.toFloat(), paint)
        canvas.drawLine(width * (2 * X_PARTITION_RATIO), 0f, width * (2 * X_PARTITION_RATIO), height.toFloat(), paint)
    }

    private fun drawHorizontalLines(canvas: Canvas) {
        canvas.drawLine(0f, height * Y_PARTITION_RATIO, width.toFloat(), height * Y_PARTITION_RATIO, paint)
        canvas.drawLine(0f, height * (2 * Y_PARTITION_RATIO), width.toFloat(), height * (2 * Y_PARTITION_RATIO), paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        val index = getNumberOfSelectedCell(x, y)
        cellPressListener?.onCellPressed(index)

        return super.onTouchEvent(event)
    }

    private fun getNumberOfSelectedCell(x: Float, y: Float) : Int {
        val xCoord= when {
            y < height * Y_PARTITION_RATIO -> {
                0
            }
            y < height * (2 * Y_PARTITION_RATIO) -> {
                1
            }
            else -> {
                2
            }
        }

        val yCoord = when {
            x < width * X_PARTITION_RATIO -> {
                0
            }
            x < width * (2 * X_PARTITION_RATIO) -> {
                1
            }
            else -> {
                2
            }
        }

        val index = numberOfRows * xCoord + yCoord
        Toast.makeText(context, "Coordinate: $xCoord : $yCoord and index: $index", Toast.LENGTH_LONG).show()

        return index
    }

    interface CellPressedListener {
        fun onCellPressed(index: Int)
    }
}