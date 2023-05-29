package com.example.animesearch.search

import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.OrderBy
import com.example.animesearch.search.model.dto.AnimeDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime")
    fun animesByFilters(
        @Query("page") page: Int? = null,
        @Query("type") type: AnimeType?,
        @Query("min_score") minScore: Double?,
        @Query("status") status: AnimeStatus?,
        @Query("sfw") sfw: Boolean = true,
        @Query("genres") genres: String?,
        @Query("order_by") orderBy: OrderBy?,
    ): Single<AnimeDto>

    @GET("top/anime")
    fun topAnime(@Query("page") page: Int? = null): Single<AnimeDto>
}