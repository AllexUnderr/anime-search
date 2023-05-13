package com.example.animesearch.filter

import com.example.animesearch.filter.model.dto.GenreDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FilterApi {
    @GET("genres/anime")
    fun animeGenres(@Query("filter") filter: String = "genres"): Single<GenreDto>
}