package com.example.animesearch.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animesearch.R
import com.example.animesearch.databinding.RecyclerviewAnimeItemBinding
import com.example.animesearch.search.model.Anime

class AnimeRecyclerAdapter :
    ListAdapter<Anime, AnimeRecyclerAdapter.ViewHolder>(AnimeItemCallback()) {

    class ViewHolder(binding: RecyclerviewAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val animeImageView: ImageView = binding.animeImageView
        val nameTextView: TextView = binding.nameTextView
        val episodeTextView: TextView = binding.episodesTextView
        val scoreTextView: TextView = binding.scoreTextView
        val scoredByTextView: TextView = binding.scoredByTextView
        val rankTextView: TextView = binding.rankTextView
        val popularityTextView: TextView = binding.popularityTextView
        val yearTextView: TextView = binding.yearTextView
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

        Glide
            .with(holder.itemView)
            .load(anime.imageUrl)
            .centerCrop()
            .into(holder.animeImageView)

        with(holder) {
            nameTextView.text = anime.name
            episodeTextView.text = anime?.episodeCount?.toString() ?: itemView.context.getString(R.string.unknown)
            scoreTextView.text = anime.score.toString()
            scoredByTextView.text = anime.scoredBy.toString()
            rankTextView.text = anime.rank.toString()
            popularityTextView.text = anime.popularity.toString()
            yearTextView.text = anime.year?.toString() ?: itemView.context.getString(R.string.unknown)
        }
    }

    private class AnimeItemCallback : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime) =
            oldItem == newItem
    }
}