package com.example.animesearch.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animesearch.R
import com.example.animesearch.databinding.RecyclerviewAnimeItemBinding
import com.example.animesearch.search.model.Anime

class SearchPagingAdapter :
    PagingDataAdapter<Anime, SearchPagingAdapter.ViewHolder>(AnimeItemCallback()) {

    class ViewHolder(val binding: RecyclerviewAnimeItemBinding) : RecyclerView.ViewHolder(binding.root)

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
            .load(anime?.imageUrl)
            .centerCrop()
            .into(holder.binding.animeImageView)

        with(holder) {
            binding.nameTextView.text = anime?.name.toStringOrUnknown(itemView)
            binding.episodesTextView.text = anime?.episodeCount.toStringOrUnknown(itemView)
            binding.scoreTextView.text = anime?.score.toStringOrUnknown(itemView)
            binding.scoredByTextView.text = anime?.scoredBy.toStringOrUnknown(itemView)
            binding.rankTextView.text = anime?.rank.toStringOrUnknown(itemView)
            binding.popularityTextView.text = anime?.popularity.toStringOrUnknown(itemView)
            binding.yearTextView.text = anime?.year.toStringOrUnknown(itemView)
        }
    }

    private fun Any?.toStringOrUnknown(itemView: View): String =
        this?.toString() ?: itemView.context.getString(R.string.unknown)

    private class AnimeItemCallback : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime) =
            oldItem == newItem
    }
}