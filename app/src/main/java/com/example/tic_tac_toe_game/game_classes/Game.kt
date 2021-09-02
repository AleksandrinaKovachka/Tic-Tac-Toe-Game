package com.example.tic_tac_toe_game.game_classes

class Game(private val botPlayer: Boolean) {
    private val board = Board(3)
    private var currentPlayer = CellState.O
    private var previousPlayer = CellState.X
    private val isBot = botPlayer
    private var gameState: GameStatesListener? = null

    fun makeMove(x: Int, y: Int) {
        board.makeMove(x, y, currentPlayer)
        //change view with player move
        gameState?.changeView(x, y)
        //change player
        changePlayer()
        if (!checkBoard() && isBot) {
            //makeBotMove
            val coord = board.makeBotMove()
            //change player
            changePlayer()
            //change view with bot move
            gameState?.changeView(x, y)
        }
    }

    private fun checkBoard() : Boolean {
        if (board.checkForWinner()) {
            gameState?.hasWinner(currentPlayer.toString())
            board.resetCells()
            return true
        }

        if (board.isFull()) {
            gameState?.fullBoard()
            board.resetCells()
            return true
        }
        return false
    }

    private fun changePlayer() {
        val state = currentPlayer
        currentPlayer = previousPlayer
        previousPlayer = state
    }

    interface GameStatesListener {
        fun hasWinner(winner: String)
        fun fullBoard()
        fun changeView(x: Int, y: Int)
    }
}