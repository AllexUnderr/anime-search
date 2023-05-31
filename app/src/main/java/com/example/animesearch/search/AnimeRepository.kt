package com.example.animesearch.search

import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.model.Anime
import com.example.animesearch.search.model.dto.AnimeDto

class AnimeRepository(private val animeApi: AnimeApi) {

    suspend fun getAnimeList(): List<Anime> = convertAnimeDto(animeApi.topAnime())

    suspend fun getFilteredAnimeList(filters: AnimeSearchFilters): List<Anime> =
        convertAnimeDto(
            animeApi.animesByFilters(
                type = filters.type,
                minScore = filters.minScore,
                status = filters.status,
                genres = filters.genres?.joinToString(",") { it.id.toString() },
                orderBy = filters.orderBy
            )
        )

    fun removeEmptyRankAnimes(anime: List<Anime>): List<Anime> =
        anime.filter { it.rank != null && it.rank != 0 }

    private fun convertAnimeDto(animeDto: AnimeDto): List<Anime> =
        animeDto.data.map {
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