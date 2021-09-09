package com.example.tic_tac_toe_game.viewModels

import androidx.lifecycle.ViewModel
import com.example.tic_tac_toe_game.game_classes.Game
import com.example.tic_tac_toe_game.game_classes.Move

class GameViewModel : ViewModel() {
    private val game = Game(3)
    var hasWinner = true
    var isFull = true
    var isBot = true
    lateinit var winnerCells : Pair<String, Int>

    fun makeMove(x: Int, y: Int) : List<Move> {
        val moves = game.makeMove(x, y)
        hasWinner = game.checkForWinner()
        if (hasWinner) {
            winnerCells = game.getWinnerCells()
        }
        isFull = game.checkBoardIsFull()
        return moves
    }

    fun getCurrentPlayer() : String {
        return game.getCurrentPlayer()
    }

    fun resetBoard() {
        game.resetBoard()
    }

    fun resetFirstPlayer() {
        game.changePlayerInNewGame()
    }

    fun changePlayerMode(isBotMode: Boolean) {
        isBot = isBotMode
        game.changePlayerMode(isBotMode)
    }
}
