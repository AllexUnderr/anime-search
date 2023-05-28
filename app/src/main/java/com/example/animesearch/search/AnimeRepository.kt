package com.example.animesearch.search

import com.example.animesearch.filter.model.Filter
import com.example.animesearch.filter.model.Genre
import com.example.animesearch.filter.model.MinScore
import com.example.animesearch.search.model.Anime
import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.OrderBy
import com.example.animesearch.search.model.dto.AnimeDto
import io.reactivex.Single

class AnimeRepository(private val animeApi: AnimeApi) {

    fun getAnimeList(): Single<List<Anime>> = animeApi.topAnime().map { convertAnimeDto(it) }

    fun getFilteredAnimeList(filters: List<Filter>): Single<List<Anime>> =
        animeApi.animesByFilters(
            type = filters.filterIsInstance<AnimeType>().first(),
            minScore = filters.filterIsInstance<MinScore>().first().score,
            status = filters.filterIsInstance<AnimeStatus>().first(),
            genres = filters.filterIsInstance<Genre>().joinToString(separator = ",") { it.id.toString() },
            orderBy = filters.filterIsInstance<OrderBy>().first()
        ).map {
            convertAnimeDto(it)
        }

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