package com.example.tic_tac_toe_game.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tic_tac_toe_game.game_classes.Game

class GameViewModel : ViewModel() {
    private val game = Game(true, 3)
    var newBoard = true
    //new board
    var hasWinner = true
    var isFull = true

    fun makeMove(x: Int, y: Int) {
        newBoard = game.makeMove(x, y)
        if (!newBoard) {
            hasWinner = game.checkForWinner()
            isFull = game.checkBoardIsFull()
        }
    }

    fun getCurrentPlayer() : String {
        return game.getCurrentPlayer()
    }

}
