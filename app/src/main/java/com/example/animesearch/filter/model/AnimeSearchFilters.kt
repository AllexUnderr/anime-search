package com.example.animesearch.filter.model

import android.os.Parcelable
import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.OrderBy
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeSearchFilters(
    val type: AnimeType?,
    val minScore: Double?,
    val genres: List<Genre>,
    val status: AnimeStatus?,
    val orderBy: OrderBy?,
) : Parcelable {
    companion object {
        val Empty = AnimeSearchFilters(
            type = null,
            minScore = null,
            genres = emptyList(),
            status = null,
            orderBy = null,
        )
    }
}