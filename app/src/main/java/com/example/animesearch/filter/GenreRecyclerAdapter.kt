package com.example.animesearch.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animesearch.databinding.RecyclerviewGenreItemBinding
import com.example.animesearch.filter.model.GenreListItem

class GenreRecyclerAdapter : ListAdapter<GenreListItem, GenreRecyclerAdapter.ViewHolder>(AnimeItemCallback()) {

    class ViewHolder(binding: RecyclerviewGenreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: GenreListItem

        init {
            binding.genreCheckBox.setOnClickListener { item.click() }
        }

        private val genreCheckBox: CheckBox = binding.genreCheckBox

        fun onBindViewHolder(item: GenreListItem) {
            this.item = item

            genreCheckBox.text = item.genre.name
            genreCheckBox.isChecked = item.isChecked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RecyclerviewGenreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(
            getItem(position)
        )
    }

    private class AnimeItemCallback : DiffUtil.ItemCallback<GenreListItem>() {
        override fun areItemsTheSame(oldItem: GenreListItem, newItem: GenreListItem) =
            oldItem.genre.id == newItem.genre.id

        override fun areContentsTheSame(oldItem: GenreListItem, newItem: GenreListItem) =
            oldItem == newItem
    }
}