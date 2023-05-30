package com.example.animesearch.search

import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.model.Anime
import com.example.animesearch.search.model.dto.AnimeDto
import io.reactivex.Single

class AnimeRepository(private val animeApi: AnimeApi) {

    fun getAnimeList(): Single<List<Anime>> = animeApi.topAnime().map { convertAnimeDto(it) }

    fun getFilteredAnimeList(filters: AnimeSearchFilters): Single<List<Anime>> =
        animeApi.animesByFilters(
            type = filters.type,
            minScore = filters.minScore,
            status = filters.status,
            genres = filters.genres?.joinToString(",") { it.id.toString() },
            orderBy = filters.orderBy
        ).map {
            convertAnimeDto(it)
        }

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