package com.example.animesearch.filter

import com.example.animesearch.filter.model.dto.GenreDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FilterApi {
    @GET("genres/anime")
    suspend fun animeGenres(@Query("filter") filter: String = "genres"): GenreDto
}