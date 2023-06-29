package com.example.animesearch.search

import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.model.Anime
import com.example.animesearch.search.model.dto.AnimesDto

class SearchRepository(private val searchApi: SearchApi) {

    suspend fun getAnimeList(page: Int, filters: AnimeSearchFilters) =
        if (filters == AnimeSearchFilters.Empty) getTopAnimeList(page)
        else getFilteredAnimeList(page, filters)

    private suspend fun getTopAnimeList(page: Int): List<Anime> = searchApi.topAnime(page).toModel()

    private suspend fun getFilteredAnimeList(page: Int, filters: AnimeSearchFilters): List<Anime> =
        searchApi.animeByFilters(
            page = page,
            type = filters.type,
            minScore = filters.minScore,
            status = filters.status,
            genres = filters.genres.joinToString(",") { it.id.toString() },
            orderBy = filters.orderBy
        ).toModel()

    private fun AnimesDto.toModel(): List<Anime> =
        data.map {
            Anime(
                id = it.malId,
                imageUrl = it.images.jpg.imageUrl,
                name = it.title,
                episodeCount = it.episodes,
                score = it.score,
                scoredBy = it.scoredBy,
                rank = it.rank,
                popularity = it.popularity,
                year = it.year
            )
        }
}