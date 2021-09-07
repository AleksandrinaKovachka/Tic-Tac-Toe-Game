package com.example.tic_tac_toe_game.game_classes

class Board(private val numberOfRows: Int) {
    private val count = numberOfRows
    private var cells = Array(count) {Array(count) {CellState.EMPTY} }

    fun makeMove(x: Int, y: Int, state: CellState) : Boolean {
        if (cells[x][y] == CellState.EMPTY) {
            cells[x][y] = state
            return true
        }
        return false
    }

    fun resetCells() {
        cells = Array(count) {Array(count) {CellState.EMPTY} }
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
                return true
            }
        }

        return false
    }

    private fun checkDiagonal() : Boolean {
        for (i in 0 until count) {
            if (cells[i][i] != cells[i + 1][i + 1] || cells[i][i] == CellState.EMPTY) {
                return false
            }
        }

        return true
    }

    private fun checkSecondDiagonal() : Boolean {
        var index = count - 1

        for (i in 0 until count){
            if (cells[i][index] != cells[i + 1][index - 1] || cells[i][i] == CellState.EMPTY){
                return false
            }
            index -= 1
        }

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

//    private val numberOfRows = 3
//    private var cells = Array(numberOfRows * numberOfRows) {CellState.EMPTY}
//
//    fun makeMove(index: Int, state: CellState) : Array<CellState> {
//        cells[index] = state
//
//        return cells
//    }
//
//    fun resetCells() {
//        cells = Array(numberOfRows * numberOfRows) {CellState.EMPTY}
//    }
//
//    fun checkForWinner() : Boolean {
//        if (checkHorizontal() || checkVertical() || checkDiagonals()) {
//            return true
//        }
//
//        return false
//    }
//
//    private fun checkHorizontal() : Boolean {
//        var index = 0
//        var array : Array<CellState>
//        var haveWinner = true
//        while (index <= cells.size) {
//            array = cells.slice(IntRange(index, index + numberOfRows)).toTypedArray()
//            haveWinner = true
//            for (i in array.indices - 1) {
//                if (array[i] != array[i + 1]) {
//                    haveWinner = false
//                    break
//                }
//            }
//            if (haveWinner) {
//                return true
//            }
//            index += numberOfRows
//        }
//
//        return false
//    }
//
//    private fun checkVertical() : Boolean {
//        var index = numberOfRows
//        for (i in 0..numberOfRows) {
//            if (cells[i] == cells[index] && cells[index] == cells[index + numberOfRows]) {
//                return true
//            }
//            index += 1
//        }
//
//        return false
//    }
//
//    private fun checkDiagonals() : Boolean {
//        if (cells[0] == cells[numberOfRows + 1] && cells[0] == cells[cells.size - 1]) {
//            return true
//        }
//
//        if (cells[numberOfRows - 1] == cells[numberOfRows + 1] && cells[numberOfRows + 1] == cells[numberOfRows * 2]) {
//            return true
//        }
//
//        return false
//    }
//
//    fun isFull() : Boolean {
//        return !cells.contains(CellState.EMPTY)
//    }
}