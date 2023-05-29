package com.example.animesearch.search.model

data class Anime(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val episodeCount: Int?,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val year: Int?,
)