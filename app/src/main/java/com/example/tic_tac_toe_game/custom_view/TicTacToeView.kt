package com.example.tic_tac_toe_game.custom_view

import android.R.attr.path
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
import com.example.tic_tac_toe_game.game_classes.Move


class TicTacToeView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private val paint = Paint()
    private var path = Path()
    private val X_PARTITION_RATIO = 1 / 3f
    private val Y_PARTITION_RATIO = 1 / 3f
    private val numberOfRows = 3
    private lateinit var board: Array<Array<Rect>>
    private lateinit var boardStates: Array<Array<String>>

    private val textPaint = Paint()

    var cellPressListener: CellPressedListener? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = Math.min(measuredHeight, measuredWidth)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        init()
    }

    private fun init() {
        paint.isAntiAlias = true
        textPaint.isAntiAlias = true
        textPaint.textSize = resources.displayMetrics.scaledDensity * 70

        board = Array(3) { Array(3) { Rect() } }
        boardStates = Array(3) { Array(3) { "" } }

        val xUnit = (width * X_PARTITION_RATIO).toInt()
        val yUnit = (height * Y_PARTITION_RATIO).toInt()

        for (j in 0 until numberOfRows) {
            for (i in 0 until numberOfRows) {
                board[i][j] = Rect(i * xUnit, j * yUnit, (i + 1) * xUnit, (j + 1) * yUnit)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawVerticalLines(canvas)
        drawHorizontalLines(canvas)
        drawBoard(canvas)
    }

    private fun drawBoard(canvas: Canvas) {
        for ((i, textArray) in boardStates.withIndex()) {
            for ((j, text) in textArray.withIndex()) {
                if (text.isNotEmpty()) {
                    drawCell(canvas, board[i][j], text)
                }
            }
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

    fun fillCell(moves: List<Move>) {
        for (item in moves) {
            boardStates[item.x][item.y] = item.state
        }

        invalidate()
    }

    private fun drawCell(canvas: Canvas, rect: Rect, state: String) {
        val xOffset = textPaint.measureText(state) * 0.5f
        val yOffset = textPaint.fontMetrics.ascent * -0.4f
        val textX = (rect.exactCenterY()) - xOffset
        val textY = (rect.exactCenterX()) + yOffset
        canvas.drawText(state, textX, textY, textPaint)
    }

    fun resetView() {
        boardStates = Array(3) { Array(3) { "" } }
        path.reset()
        invalidate()
    }

    interface CellPressedListener {
        fun onCellPressed(x: Int, y: Int)
    }
}