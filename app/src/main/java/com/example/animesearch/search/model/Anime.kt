package com.example.animesearch.search.model

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("mal_id") val id: Int,
    @SerializedName("title_english") val animeName: String,
    val episodes: Int,
    val score: Double,
    val year: Int,
)