package com.example.tic_tac_toe_game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tic_tac_toe_game.adapters.ScoreAdapter
import com.example.tic_tac_toe_game.databinding.FragmentStatisticsBinding
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null

    private val binding get() = _binding!!

    private var playerBot : Int = 0
    private var bot : Int = 0
    private var playerO : Int = 0
    private var playerX : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        setupRecyclerViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        val preferences = this.activity?.getSharedPreferences("Game_Statistics", Context.MODE_PRIVATE)
        if (preferences != null) {
            playerBot = preferences.getInt("PlayerWithBot", 0)
        }
        if (preferences != null) {
            bot = preferences.getInt("Bot", 0)
        }
        if (preferences != null) {
            playerO = preferences.getInt("PlayerO", 0)
        }
        if (preferences != null) {
            playerX = preferences.getInt("PlayerX", 0)
        }
    }

    private fun setupRecyclerViews() {
        binding.onePlayerRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.twoPlayerRecyclerView.layoutManager = GridLayoutManager(context, 2)

        val onePlayerAdapter = ScoreAdapter()
        binding.onePlayerRecyclerView.adapter = onePlayerAdapter

        val listOnePlayer = listOf("X", bot.toString(), "O", playerBot.toString())
        lifecycle.coroutineScope.launch{
            onePlayerAdapter.submitList(listOnePlayer)
        }

        val twoPlayerAdapter = ScoreAdapter()
        binding.twoPlayerRecyclerView.adapter = twoPlayerAdapter

        val listTwoPlayer = listOf("X", playerX.toString(), "O", playerO.toString())
        lifecycle.coroutineScope.launch{
            twoPlayerAdapter.submitList(listTwoPlayer)
        }
    }
}