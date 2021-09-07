package com.example.tic_tac_toe_game.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tic_tac_toe_game.game_classes.Game
import com.example.tic_tac_toe_game.game_classes.Move

class GameViewModel : ViewModel() {
    private val game = Game(true, 3)
    var newBoard = true
    var hasWinner = true
    var isFull = true
    var isBotGame = true
    lateinit var botMove : Pair<Int, Int>
    //lateinit var moves : List<Move>
    //if is bot - move of bot
    //array with new moves

    fun makeMove(x: Int, y: Int) : List<Move> {
        val moves = game.makeMove(x, y)
        hasWinner = game.checkForWinner()
        isFull = game.checkBoardIsFull()
        return moves
//        newBoard = game.makeMove(x, y)
//        if (newBoard) {
//            if (isBotGame) {
//                botMove = game.getBotMove()
//            }
//            hasWinner = game.checkForWinner()
//            isFull = game.checkBoardIsFull()
//        }
    }

    fun getCurrentPlayer() : String {
        return game.getCurrentPlayer()
    }

}
