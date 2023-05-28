package com.example.animesearch.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animesearch.databinding.RecyclerviewGenreItemBinding
import com.example.animesearch.filter.model.Genre

class GenreRecyclerAdapter(private val checkListener: OnGenreCheckListener) :
    ListAdapter<Genre, GenreRecyclerAdapter.ViewHolder>(AnimeItemCallback()) {

    interface OnGenreCheckListener {
        fun onGenreCheck(genre: Genre)

        fun onGenreUncheck(genre: Genre)
    }

    class ViewHolder(binding: RecyclerviewGenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameCheckBox: CheckBox = binding.genreCheckBox
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
        val genre = getItem(position)

        holder.nameCheckBox.text = genre.name
        holder.nameCheckBox.setOnClickListener {
            if (holder.nameCheckBox.isChecked)
                checkListener.onGenreCheck(genre)
            else
                checkListener.onGenreUncheck(genre)
        }
    }

    private class AnimeItemCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) =
            oldItem == newItem
    }
}