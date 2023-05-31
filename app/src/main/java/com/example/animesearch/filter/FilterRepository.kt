package com.example.animesearch.filter

import com.example.animesearch.filter.model.Genre
import com.example.animesearch.filter.model.database.GenreDao
import com.example.animesearch.filter.model.database.GenreEntity
import com.example.animesearch.filter.model.dto.GenreDto

class FilterRepository(private val filterApi: FilterApi, private val genreDao: GenreDao) {

    suspend fun getGenreList(): List<Genre> {
        val genreList = genreDao.getGenres()
        if (genreList.isNotEmpty())
            return convertEntityToGenre(genreList)

        val downloadedGenres = convertDtoToGenre(filterApi.animeGenres())
        genreDao.insertGenreList(
            convertModelToEntity(downloadedGenres)
        )

        return downloadedGenres
    }

    private fun convertDtoToGenre(genreDto: GenreDto): List<Genre> =
        genreDto.data.map { Genre(it.malId, it.name) }

    private fun convertEntityToGenre(genreEntity: List<GenreEntity>): List<Genre> =
        genreEntity.map { Genre(it.id, it.name) }

    private fun convertModelToEntity(genres: List<Genre>): List<GenreEntity> =
        genres.map { GenreEntity(it.id, it.name) }
}