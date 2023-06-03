package com.example.animesearch.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.model.Anime

class AnimePagingSource(
    private val animeRepository: AnimeRepository, private val filters: AnimeSearchFilters
) : PagingSource<Int, Anime>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        val position = params.key ?: 1
        return try {
            LoadResult.Page(
                data = animeRepository.getAnimeList(position, filters),
                prevKey = if (position != 1) position - 1 else null,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        val anchorPosition = state.anchorPosition
            ?: return null

        return state.closestPageToPosition(anchorPosition)
            ?.prevKey
            ?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)
                ?.nextKey
                ?.minus(1)
    }
}