package com.example.tic_tac_toe_game.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tic_tac_toe_game.game_classes.Game

class GameViewModel : ViewModel() {
    private val game = Game(true, 3)
    var newBoard = true
    var hasWinner = true
    var isFull = true
    var isBotGame = true
    lateinit var botMove : Pair<Int, Int>
    //if is bot - move of bot
    //array with new moves

    fun makeMove(x: Int, y: Int) {
        newBoard = game.makeMove(x, y)
        if (newBoard) {
            if (isBotGame) {
                botMove = game.getBotMove()
            }
            hasWinner = game.checkForWinner()
            isFull = game.checkBoardIsFull()
        }
    }

    fun getCurrentPlayer() : String {
        return game.getCurrentPlayer()
    }

}
