package com.example.animesearch.filter.model

import com.example.animesearch.search.model.LocalizableItem

data class SelectableChip(
    val item: LocalizableItem,
    val isSelected: Boolean,
)