package com.example.animesearch.search.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimesDto(
    @SerialName("data") val data: List<AnimeDto>
)