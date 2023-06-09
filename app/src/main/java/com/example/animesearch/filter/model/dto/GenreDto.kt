package com.example.animesearch.filter.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("mal_id") val malId: Int,
    @SerialName("name")   val name: String,
)