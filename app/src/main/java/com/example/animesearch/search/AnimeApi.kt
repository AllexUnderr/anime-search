package com.example.animesearch.search

import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.Animes
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface AnimeApi {
    @GET("anime")
    fun animesByFilters(
        @Query("page") page: Int? = null,
        @Query("type") type: AnimeType? = null,
        @Query("min_score") minScore: Double? = null,
        @Query("status") status: AnimeStatus? = null,
        @Query("sfw") sfw: Boolean = true,
        @Query("genres") genres: String? = null
    ): Call<Animes>

    @GET("genres/anime")
    fun animeGenres(@Query("filter") filter: String = "genres")

    @GET("top/anime")
    fun topAnime(@Query("page") page: Int? = null): Call<Animes>
}