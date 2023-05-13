package com.example.animesearch.filter

import com.example.animesearch.filter.model.Genre
import com.example.animesearch.filter.model.database.GenreDao
import com.example.animesearch.filter.model.database.GenreEntity
import com.example.animesearch.filter.model.dto.GenreDto
import io.reactivex.Single

class FilterRepository(private val filterApi: FilterApi, private val genreDao: GenreDao) {

    fun getGenreList(): Single<List<Genre>> {
        return genreDao.getGenres().flatMap { genres ->
            if (genres.isEmpty()) {
                filterApi.animeGenres().doOnSuccess {
                    genreDao.insertGenreList(
                        convertDtoToEntity(it)
                    )
                }.map { convertDtoToGenre(it) }
            } else {
                Single.just(convertEntityToGenre(genres))
            }
        }
    }

    private fun convertDtoToEntity(genreDto: GenreDto): List<GenreEntity> =
        genreDto.data.map { GenreEntity(it.malId, it.name) }

    private fun convertDtoToGenre(genreDto: GenreDto): List<Genre> =
        genreDto.data.map { Genre(it.malId, it.name) }

    private fun convertEntityToGenre(genreEntity: List<GenreEntity>): List<Genre> =
        genreEntity.map { Genre(it.id, it.name) }
}