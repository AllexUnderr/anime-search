package com.example.animesearch.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animesearch.databinding.RecyclerviewAnimeItemBinding
import com.example.animesearch.search.model.Anime

class AnimeRecyclerAdapter :
    ListAdapter<Anime, AnimeRecyclerAdapter.ViewHolder>(AnimeItemCallback()) {

    class ViewHolder(binding: RecyclerviewAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idTextView: TextView = binding.id
        val nameTextView: TextView = binding.name
        val scoreTextView: TextView = binding.score
        val episodeTextView: TextView = binding.episodes
        val yearTextView: TextView = binding.year
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RecyclerviewAnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = getItem(position)
        holder.idTextView.text = anime.id.toString()
        holder.nameTextView.text = anime.animeName
        holder.scoreTextView.text = anime.score.toString()
        holder.episodeTextView.text = anime.episodes.toString()
        holder.yearTextView.text = anime.year.toString()
    }

    private class AnimeItemCallback : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime) =
            oldItem == newItem
    }
}