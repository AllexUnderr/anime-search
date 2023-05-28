package com.example.animesearch.search.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(@SerialName("jpg") val jpg: JpgImage) {
    @Serializable
    data class JpgImage(@SerialName("image_url") val imageUrl: String)
}