package com.example.tic_tac_toe_game

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tic_tac_toe_game.custom_view.TicTacToeView
import com.example.tic_tac_toe_game.databinding.FragmentGameBinding
import com.example.tic_tac_toe_game.viewModels.GameViewModel
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class GameFragment : Fragment(), TicTacToeView.CellPressedListener {

    private lateinit var gameViewModel: GameViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ticTacToeView.cellPressListener = this
        binding.information.text = "${gameViewModel.getCurrentPlayer()}`s turn"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onCellPressed(x: Int, y: Int) {
        val moves = gameViewModel.makeMove(x, y)
        if (moves.isNotEmpty()) {
            binding.ticTacToeView.fillCell(moves)
            binding.information.text = "${gameViewModel.getCurrentPlayer()}`s turn"
        } else {
            binding.information.text = "Cell is already selected\n${gameViewModel.getCurrentPlayer()}`s turn"
        }

        if (gameViewModel.hasWinner) {
            saveWinnerScore()
            binding.ticTacToeView.drawWinnerLine(gameViewModel.winnerCells)
            //Log.i("TAG", "Test winner cells: ${gameViewModel.winnerCells}")
            resetGame("Winner is ${gameViewModel.getCurrentPlayer()}`s player")
        } else if (gameViewModel.isFull) {
            resetGame("Equality")
            gameViewModel.resetFirstPlayer()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun resetGame(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(TimeUnit.SECONDS.toMillis(3))
            withContext(Dispatchers.Main) {
                binding.ticTacToeView.resetView()
                gameViewModel.resetBoard()
                binding.information.text = "${gameViewModel.getCurrentPlayer()}`s turn"
            }
        }
        binding.information.text = message
    }

    private fun saveWinnerScore() {
        val name = if (gameViewModel.isBot) {
            if (gameViewModel.getCurrentPlayer() == "O") {
                "PlayerWithBot"
            } else {
                "Bot"
            }
        } else {
            "Player${gameViewModel.getCurrentPlayer()}"
        }

        val preferences = this.activity?.getSharedPreferences("Game_Statistics", Context.MODE_PRIVATE)
        val total = preferences?.getInt(name, 0)
        val editor = preferences?.edit()
        editor?.apply {
            if (total != null) {
                putInt(name, total + 1)
            }
        }?.apply()
    }
}