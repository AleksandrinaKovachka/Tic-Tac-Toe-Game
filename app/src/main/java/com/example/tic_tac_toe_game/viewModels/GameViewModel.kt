package com.example.tic_tac_toe_game.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tic_tac_toe_game.game_classes.Game
import com.example.tic_tac_toe_game.game_classes.Move

class GameViewModel : ViewModel() {
    private val game = Game(false, 3)
    var hasWinner = true
    var isFull = true
    var isBot = false
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
}
