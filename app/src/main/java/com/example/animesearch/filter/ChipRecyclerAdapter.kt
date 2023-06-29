package com.example.animesearch.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animesearch.databinding.RecyclerviewChipItemBinding
import com.example.animesearch.filter.model.SelectableChip
import com.example.animesearch.search.model.LocalizableItem

class ChipRecyclerAdapter(private val onClick: (LocalizableItem) -> Unit) :
    ListAdapter<SelectableChip, ChipRecyclerAdapter.ViewHolder>(ChipItemCallback()) {

    class ViewHolder(binding: RecyclerviewChipItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val chip = binding.chip
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RecyclerviewChipItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = getItem(position)

        with(holder) {
            chip.text = holder.itemView.context.getString(tag.item.localizedStringRes)
            chip.isCheckable = true
            chip.isChecked = tag.isSelected
            chip.isCheckable = !tag.isSelected
            chip.setOnClickListener {
                onClick(tag.item)
            }
        }
    }

    private class ChipItemCallback : DiffUtil.ItemCallback<SelectableChip>() {
        override fun areItemsTheSame(oldItem: SelectableChip, newItem: SelectableChip) =
            oldItem.item == newItem.item

        override fun areContentsTheSame(oldItem: SelectableChip, newItem: SelectableChip) =
            oldItem == newItem
    }
}