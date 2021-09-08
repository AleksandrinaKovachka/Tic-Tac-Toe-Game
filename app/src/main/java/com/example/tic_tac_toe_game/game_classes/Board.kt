package com.example.tic_tac_toe_game.game_classes

class Board(numberOfRows: Int) {
    private val count = numberOfRows
    private var cells = Array(count) {Array(count) {CellState.EMPTY} }
    private lateinit var winnerCells: Pair<String, Int>

    fun makeMove(x: Int, y: Int, state: CellState) : Boolean {
        if (cells[x][y] == CellState.EMPTY) {
            cells[x][y] = state
            return true
        }
        return false
    }

    fun resetCells() {
        cells = Array(count) {Array(count) {CellState.EMPTY} }
        winnerCells = Pair("", -1)
    }

    fun getWinnerCells() : Pair<String, Int> {
        return winnerCells
    }

    fun checkForWinner() : Boolean {
        if (checkHorizontal() || checkVertical() || checkDiagonal() || checkSecondDiagonal()) {
            return true
        }
        return false
    }

    private fun checkHorizontal() : Boolean {
        var haveWinner : Boolean
        for (i in 0 until count) {
            haveWinner = true
            for (j in 0..count - 2) {
                if (cells[i][j] != cells[i][j + 1] || cells[i][j] == CellState.EMPTY) {
                    haveWinner = false
                    break
                }
            }
            if (haveWinner) {
                winnerCells = Pair("h", i)
                return true
            }
        }

        return false
    }

    private fun checkVertical() : Boolean {
        var haveWinner : Boolean
        for (j in 0 until count) {
            haveWinner = true
            for (i in 0..count - 2) {
                if (cells[i][j] != cells[i + 1][j] || cells[i][j] == CellState.EMPTY) {
                    haveWinner = false
                    break
                }
            }
            if (haveWinner) {
                winnerCells = Pair("v", j)
                return true
            }
        }

        return false
    }

    private fun checkDiagonal() : Boolean {
        for (i in 0..count - 2) {
            if (cells[i][i] != cells[i + 1][i + 1] || cells[i][i] == CellState.EMPTY) {
                return false
            }
        }
        winnerCells = Pair("d", 0)
        return true
    }

    private fun checkSecondDiagonal() : Boolean {
        var index = count - 1

        for (i in 0..count - 2){
            if (cells[i][index] != cells[i + 1][index - 1] || cells[i][index] == CellState.EMPTY){
                return false
            }
            index -= 1
        }

        winnerCells = Pair("sD", 2)
        return true
    }

    fun isFull() : Boolean {
        for (i in 0 until count) {
            if (cells[i].contains(CellState.EMPTY)) {
                return false
            }
        }

        return true
    }

    fun makeBotMove() : Pair<Int, Int>{
        val freeCells: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in cells.indices) {
            for (j in cells.indices) {
                if (cells[i][j] == CellState.EMPTY) {
                    freeCells.add(Pair(i, j))
                }
            }
        }

        if (freeCells.size == 1) {
            return freeCells[0]
        }

        val randPosition = (0 until freeCells.size).random()

        return freeCells[randPosition]
    }
}