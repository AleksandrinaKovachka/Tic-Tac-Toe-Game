package com.example.tic_tac_toe_game.game_classes

import android.widget.Toast

class Game(botPlayer: Boolean, numberOfRows: Int) {
    private val board = Board(numberOfRows)
    private var currentPlayer = CellState.O
    private var previousPlayer = CellState.X
    private val isBot = botPlayer

    fun makeMove(x: Int, y: Int) : List<Move> {
        val moves : MutableList<Move> = mutableListOf()
        if (!board.makeMove(x, y, currentPlayer)) {
            return moves.toList()
        }
        moves.add(Move(x, y, currentPlayer.toString()))

        if (checkAndChangePlayer()) {
            return moves
        }

        if (isBot) {
            val botMove = board.makeBotMove()
            moves.add(Move(botMove.first, botMove.second, currentPlayer.toString()))

            if (checkAndChangePlayer()) {
                return moves
            }
        }
        return moves
    }

    private fun checkAndChangePlayer() : Boolean {
        if (checkBoard()) {
            return true
        }

        changePlayer()
        return false
    }

    private fun checkBoard() : Boolean {
        if (board.checkForWinner() || board.isFull()) {
            return true
        }

        return false
    }

    private fun changePlayer() {
        val state = currentPlayer
        currentPlayer = previousPlayer
        previousPlayer = state
    }

    fun getCurrentPlayer() : String {
        return currentPlayer.toString()
    }

    fun checkForWinner() : Boolean {
        if (board.checkForWinner()) {
            return true
        }
        return false
    }

    fun checkBoardIsFull() : Boolean {
        if (board.isFull()) {
            return true
        }
        return false
    }

    fun resetBoard() {
        board.resetCells()
    }
}