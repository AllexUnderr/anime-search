package com.example.animesearch.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animesearch.core.BaseViewModel
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.helper.SingleLiveEvent
import com.example.animesearch.search.model.Anime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class AnimeViewModel(private val animeRepository: AnimeRepository) : BaseViewModel() {

    private val filtersFlow = MutableStateFlow(AnimeSearchFilters.Empty)

    val scrollToCommand = SingleLiveEvent<Int>()

    @OptIn(ExperimentalCoroutinesApi::class)
    var animeFlow: Flow<PagingData<Anime>> =
        filtersFlow
            .flatMapLatest { filters ->
                Pager(
                    config = PagingConfig(25, enablePlaceholders = true),
                    pagingSourceFactory = { AnimePagingSource(animeRepository, filters) },
                ).flow
            }
            .cachedIn(viewModelScope)

    fun onFiltersApplied(filters: AnimeSearchFilters) {
        scrollToCommand.value = 0
        filtersFlow.value = filters
    }
}