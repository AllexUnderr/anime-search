package com.example.animesearch.filter.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresDto(
    @SerialName("data") val data: List<GenreDto>
)