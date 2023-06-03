package com.example.animesearch.filter.model

import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.OrderBy

data class AnimeSearchFilters(
    val type: AnimeType?,
    val minScore: Double?,
    val genres: List<Genre>?,
    val status: AnimeStatus?,
    val orderBy: OrderBy?,
) {
    companion object {
        val Empty = AnimeSearchFilters(
            type = null,
            minScore = null,
            genres = null,
            status = null,
            orderBy = null,
        )
    }
}