package com.example.animesearch.filter.model

data class GenreListItem(
    val genre: Genre,
    val isChecked: Boolean,
    val onClick: (GenreListItem) -> Unit
) {
    fun click() = onClick(this)
}