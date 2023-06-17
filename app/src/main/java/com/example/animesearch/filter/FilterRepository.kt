package com.example.animesearch.filter

import com.example.animesearch.filter.model.Genre
import com.example.animesearch.filter.model.database.GenreDao
import com.example.animesearch.filter.model.database.GenreEntity
import com.example.animesearch.filter.model.dto.GenresDto

class FilterRepository(private val filterApi: FilterApi, private val genreDao: GenreDao) {

    suspend fun getGenreList(): List<Genre> {
        val genreList = genreDao.getGenres()
        if (genreList.isNotEmpty())
            return genreList.toModel()

        val downloadedGenres = filterApi.animeGenres().toModel()
        genreDao.insertGenreList(downloadedGenres.toEntity())

        return downloadedGenres
    }

    private fun GenresDto.toModel(): List<Genre> =
        data.map { Genre(it.malId, it.name) }

    private fun List<GenreEntity>.toModel(): List<Genre> =
        map { Genre(it.id, it.name) }

    private fun List<Genre>.toEntity(): List<GenreEntity> =
        map { GenreEntity(it.id, it.name) }
}