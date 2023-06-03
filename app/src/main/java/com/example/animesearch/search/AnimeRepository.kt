package com.example.animesearch.search

import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.model.Anime
import com.example.animesearch.search.model.dto.AnimeDto

class AnimeRepository(private val animeApi: AnimeApi) {

    suspend fun getAnimeList(page: Int, filters: AnimeSearchFilters) =
        if (filters == AnimeSearchFilters.Empty) getTopAnimeList(page)
        else getFilteredAnimeList(page, filters)

    private suspend fun getTopAnimeList(page: Int): List<Anime> = convertAnimeDto(animeApi.topAnime(page))

    private suspend fun getFilteredAnimeList(page: Int, filters: AnimeSearchFilters): List<Anime> =
            convertAnimeDto(
                animeApi.animeByFilters(
                    page = page,
                    type = filters.type,
                    minScore = filters.minScore,
                    status = filters.status,
                    genres = filters.genres?.joinToString(",") { it.id.toString() },
                    orderBy = filters.orderBy
                )
            )

    private fun convertAnimeDto(animeDto: AnimeDto): List<Anime> = animeDto.data.map {
        Anime(
            id = it.malId,
            imageUrl = it.images.jpg.imageUrl,
            name = it.name,
            episodeCount = it.episodes,
            score = it.score,
            scoredBy = it.scoredBy,
            rank = it.rank,
            popularity = it.popularity,
            year = it.year
        )
    }
}