package com.example.tic_tac_toe_game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_game.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupResetScoreButton()

        setupPlayerModeSwitch()
        setOnSwitchChange()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resetSavedScores() {
        activity?.getSharedPreferences("Game_Statistics", Context.MODE_PRIVATE)?.edit {
            putInt("PlayerWithBot", 0)
            putInt("Bot", 0)
            putInt("PlayerO", 0)
            putInt("PlayerX", 0)
        }
    }

    private fun setupPlayerModeSwitch() {
        val preferences = this.activity?.getSharedPreferences("Player_Mode", Context.MODE_PRIVATE)
        val mode = preferences?.getBoolean("isBot", true)

        binding.playersModeSwitch.isChecked = !mode!!
    }

    private fun savePlayerMode(isBot: Boolean) {
        activity?.getSharedPreferences("Player_Mode", Context.MODE_PRIVATE)?.edit {
            putBoolean("isBot", isBot)
        }
    }

    private fun setOnSwitchChange() {
        binding.playersModeSwitch.setOnCheckedChangeListener { _, b ->
            savePlayerMode(!b)
        }
    }

    private fun setupResetScoreButton() {
        binding.resetScoreButton.setOnClickListener {
            resetSavedScores()
            findNavController().popBackStack()
        }
    }
}