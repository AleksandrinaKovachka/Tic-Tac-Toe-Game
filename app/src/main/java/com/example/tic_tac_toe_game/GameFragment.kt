package com.example.tic_tac_toe_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ticTacToeView.cellPressListener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCellPressed(x: Int, y: Int) {
        gameViewModel.makeMove(x, y)
        if (gameViewModel.newBoard) {
            binding.ticTacToeView.fillCell(x, y, gameViewModel.getCurrentPlayer())
        }
    }

    //check if have new board - custom view to make changes
    //check if have winner - custom view to clear board
    //check if board is full - custom view to clear board


}