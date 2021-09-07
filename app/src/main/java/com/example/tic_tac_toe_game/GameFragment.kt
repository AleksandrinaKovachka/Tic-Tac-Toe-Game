package com.example.tic_tac_toe_game

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_game.custom_view.TicTacToeView
import com.example.tic_tac_toe_game.databinding.FragmentGameBinding
import com.example.tic_tac_toe_game.game_classes.Game
import com.example.tic_tac_toe_game.viewModels.GameViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameFragment : Fragment(), TicTacToeView.CellPressedListener {

    private lateinit var gameViewModel: GameViewModel //= ViewModelProvider(this).get(GameViewModel::class.java)

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
            binding.information.text = "${gameViewModel.getCurrentPlayer()}`s turn"//gameViewModel.getCurrentPlayer()
        } else {
            binding.information.text = "Cell is already selected\n${gameViewModel.getCurrentPlayer()}`s turn"
        }

        if (gameViewModel.hasWinner) {
            binding.information.text = "Winner ${gameViewModel.getCurrentPlayer()}"
            binding.ticTacToeView.resetView()
        } else if (gameViewModel.isFull) {
            binding.information.text = "Equality"
            binding.ticTacToeView.resetView()
        }

//        Toast.makeText(context, "is new board: ${gameViewModel.newBoard}", Toast.LENGTH_LONG).show()
//        if (gameViewModel.newBoard) {
//            binding.ticTacToeView.fillCell(x, y, gameViewModel.getCurrentPlayer())
//            //if is bot
//            if (gameViewModel.isBotGame) {
//                binding.ticTacToeView.fillCell(gameViewModel.botMove.first, gameViewModel.botMove.second, gameViewModel.getCurrentPlayer())
//            }
//        }
    }

    //check if have new board - custom view to make changes
    //check if have winner - custom view to clear board
    //check if board is full - custom view to clear board


}