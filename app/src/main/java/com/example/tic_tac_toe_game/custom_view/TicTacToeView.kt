package com.example.tic_tac_toe_game.custom_view

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
import androidx.core.content.ContextCompat
import com.example.tic_tac_toe_game.R
import com.example.tic_tac_toe_game.game_classes.Move


class TicTacToeView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private val paint = Paint()
    private val textPaint = Paint()
    private var path = Path()
    private val xPartition = 1 / 3f
    private val yPartition = 1 / 3f
    private val numberOfRows = 3
    private lateinit var board: Array<Array<Rect>>
    private lateinit var boardStates: Array<Array<String>>
    private lateinit var winnerCells: Pair<String, Int>

    private var hasLine : Boolean = false

    var cellPressListener: CellPressedListener? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = measuredHeight.coerceAtMost(measuredWidth)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        init()
    }

    private fun init() {
        paint.isAntiAlias = true
        paint.color = ContextCompat.getColor(context, R.color.dark_green)
        paint.strokeWidth = resources.displayMetrics.density * 5
        textPaint.isAntiAlias = true
        textPaint.textSize = resources.displayMetrics.scaledDensity * 70

        board = Array(3) { Array(3) { Rect() } }
        boardStates = Array(3) { Array(3) { "" } }

        val xUnit = (width * xPartition).toInt()
        val yUnit = (height * yPartition).toInt()

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

        if (hasLine) {
            drawLine(canvas)
        }
    }

    private fun drawLine(canvas: Canvas) {
        var centerX = 0f
        var centerY  = 0f
        var centerDownX  = 0f
        var centerDownY  = 0f
        when (winnerCells.first) {
            "v" -> {
                centerX = board[winnerCells.second][0].exactCenterX()
                centerY = board[winnerCells.second][0].exactCenterY()
                centerDownX = board[winnerCells.second][numberOfRows - 1].exactCenterX()
                centerDownY = board[winnerCells.second][numberOfRows - 1].exactCenterY()
            }
            "h" -> {
                centerX = board[0][winnerCells.second].exactCenterX()
                centerY = board[0][winnerCells.second].exactCenterY()
                centerDownX = board[numberOfRows - 1][winnerCells.second].exactCenterX()
                centerDownY = board[numberOfRows - 1][winnerCells.second].exactCenterY()
            }
            "d" -> {
                centerX = board[0][0].exactCenterX()
                centerY = board[0][0].exactCenterY()
                centerDownX = board[numberOfRows - 1][numberOfRows - 1].exactCenterX()
                centerDownY = board[numberOfRows - 1][numberOfRows - 1].exactCenterY()
            }
            "sD" -> {
                centerX = board[0][winnerCells.second].exactCenterX()
                centerY = board[0][winnerCells.second].exactCenterY()
                centerDownX = board[numberOfRows - 1][0].exactCenterX()
                centerDownY = board[numberOfRows - 1][0].exactCenterY()
            }
        }

        canvas.drawLine(centerX, centerY, centerDownX, centerDownY, paint)

        hasLine = false
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
        canvas.drawLine(width * xPartition, 0f, width * xPartition, height.toFloat(), paint)
        canvas.drawLine(width * (2 * xPartition), 0f, width * (2 * xPartition), height.toFloat(), paint)
    }

    private fun drawHorizontalLines(canvas: Canvas) {
        canvas.drawLine(0f, height * yPartition, width.toFloat(), height * yPartition, paint)
        canvas.drawLine(0f, height * (2 * yPartition), width.toFloat(), height * (2 * yPartition), paint)
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
            y < height * yPartition -> {
                0
            }
            y < height * (2 * yPartition) -> {
                1
            }
            else -> {
                2
            }
        }

        val yCoord = when {
            x < width * xPartition -> {
                0
            }
            x < width * (2 * xPartition) -> {
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

    fun drawWinnerLine(winnerInfo: Pair<String, Int>) {
        winnerCells = winnerInfo

        hasLine = true
        invalidate()
    }

    interface CellPressedListener {
        fun onCellPressed(x: Int, y: Int)
    }
}