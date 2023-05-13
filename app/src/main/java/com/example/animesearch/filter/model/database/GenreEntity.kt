package com.example.animesearch.filter.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String,
)