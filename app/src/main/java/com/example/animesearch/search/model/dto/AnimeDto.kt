package com.example.animesearch.search.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDto(
    @SerialName("mal_id")       val malId: Int,
    @SerialName("images")       val images: ImageDto,
    @SerialName("title")        val title: String,
    @SerialName("episodes")     val episodes: Int?,
    @SerialName("score")        val score: Double?,
    @SerialName("scored_by")    val scoredBy: Int?,
    @SerialName("rank")         val rank: Int?,
    @SerialName("popularity")   val popularity: Int?,
    @SerialName("year")         val year: Int?,
)