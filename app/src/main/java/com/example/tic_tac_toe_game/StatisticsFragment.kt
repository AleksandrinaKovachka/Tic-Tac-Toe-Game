package com.example.tic_tac_toe_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tic_tac_toe_game.adapters.ScoreAdapter
import com.example.tic_tac_toe_game.databinding.FragmentGameBinding
import com.example.tic_tac_toe_game.databinding.FragmentStatisticsBinding
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onePlayerRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.twoPlayerRecyclerView.layoutManager = GridLayoutManager(context, 2)

        val onePlayerAdapter = ScoreAdapter()
        binding.onePlayerRecyclerView.adapter = onePlayerAdapter

        val listOnePlayer = listOf("X", "1", "O", "0")
        lifecycle.coroutineScope.launch{
            onePlayerAdapter.submitList(listOnePlayer)
        }

        val twoPlayerAdapter = ScoreAdapter()
        binding.twoPlayerRecyclerView.adapter = twoPlayerAdapter

        val listTwoPlayer = listOf("X", "1", "O", "0")
        lifecycle.coroutineScope.launch{
            twoPlayerAdapter.submitList(listTwoPlayer)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}