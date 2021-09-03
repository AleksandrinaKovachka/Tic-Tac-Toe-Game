package com.example.tic_tac_toe_game.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
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
    private var hasBoardChange = false
    private lateinit var coord: Pair<Int, Int>
    private lateinit var playerSymbol: String

    private val textPaint = Paint()

    var cellPressListener: CellPressedListener? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = Math.min(measuredHeight, measuredWidth)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawVerticalLines(canvas)
        drawHorizontalLines(canvas)
        if (hasBoardChange) {
            drawCell(canvas)
            hasBoardChange = false
        }
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

        val indexes = getNumberOfSelectedCell(x, y)
        cellPressListener?.onCellPressed(indexes.first, indexes.second)

        return super.onTouchEvent(event)
    }

    private fun getNumberOfSelectedCell(x: Float, y: Float) : Pair<Int, Int> {
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

        return Pair(xCoord, yCoord)
    }

    fun fillCell(x: Int, y: Int, symbol: String) {
        coord = Pair(x, y)
        playerSymbol = symbol
        hasBoardChange = true
        invalidate()
    }

    private fun drawCell(canvas: Canvas) {
        val xUnit = (width * X_PARTITION_RATIO).toInt()
        val yUnit = (height * Y_PARTITION_RATIO).toInt()

        val rect = Rect()
        rect.contains(coord.first * xUnit, coord.second * yUnit, (coord.first + 1) * xUnit, (coord.second + 1) * yUnit)

        val xOffset = textPaint.measureText(playerSymbol) * 0.5f
        val yOffset = textPaint.fontMetrics.ascent * -0.4f
        val textX = (rect.exactCenterX()) - xOffset
        val textY = (rect.exactCenterY()) + yOffset
        canvas.drawText(playerSymbol, textX, textY, textPaint)
    }

    fun resetView() {

    }

    interface CellPressedListener {
        fun onCellPressed(x: Int, y: Int)
    }
}