package com.example.tic_tac_toe_game.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tic_tac_toe_game.databinding.PlayerStatisticsLayoutBinding

class ScoreAdapter : ListAdapter<String, ScoreAdapter.ScoreViewHolder>(ScoreComparator()) {

    class ScoreViewHolder(private val binding: PlayerStatisticsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(score: String) {
            binding.playerOrScoreTextView.text = score
        }
    }

    class ScoreComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder(
            PlayerStatisticsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}