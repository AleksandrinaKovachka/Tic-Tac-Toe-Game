package com.example.tic_tac_toe_game.game_classes

class Game(botPlayer: Boolean, numberOfRows: Int) {
    private val board = Board(numberOfRows)
    private var currentPlayer = CellState.O
    private var previousPlayer = CellState.X
    private val isBot = botPlayer
    private lateinit var botMove : Pair<Int, Int>
    //private var gameState: GameStatesListener? = null

    fun makeMove(x: Int, y: Int) : List<Move> {
        var moves : MutableList<Move> = mutableListOf()
        if (!board.makeMove(x, y, currentPlayer)) {
            return moves.toList()
        }
        moves.add(Move(x, y, currentPlayer.toString()))
        changePlayer()

        if (!checkBoard() && isBot) {
            botMove = board.makeBotMove()
            moves.add(Move(botMove.first, botMove.second, currentPlayer.toString()))
            changePlayer()
        }
        return moves
    }

    private fun checkBoard() : Boolean {
        if (board.checkForWinner()) {
            //gameState?.hasWinner(currentPlayer.toString())
            return true
        }

        if (board.isFull()) {
            //gameState?.fullBoard()
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
            board.resetCells()
            return true
        }
        return false
    }

    fun checkBoardIsFull() : Boolean {
        if (board.isFull()) {
            //gameState?.fullBoard()
            board.resetCells()
            return true
        }
        return false
    }

    fun getBotMove() : Pair<Int, Int> {
        return botMove
    }


//    interface GameStatesListener {
//        fun hasWinner(winner: String)
//        fun fullBoard()
//        fun changeView(x: Int, y: Int)
//    }
}