package com.example.animesearch.filter.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(@SerialName("data") val data: List<GenreDetailsDto>) {
    @Serializable
    data class GenreDetailsDto(
        @SerialName("mal_id") val malId: Int,
        @SerialName("name") val name: String,
    )
}