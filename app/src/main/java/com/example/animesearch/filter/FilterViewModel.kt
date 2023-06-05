package com.example.animesearch.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.animesearch.core.BaseViewModel
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.filter.model.Genre
import com.example.animesearch.filter.model.GenreListItem
import com.example.animesearch.helper.SingleLiveEvent
import kotlinx.coroutines.launch

class FilterViewModel(
    private val filterRepository: FilterRepository,
) : BaseViewModel() {

    private val _genreList: MutableLiveData<List<GenreListItem>> = MutableLiveData()
    val genreList: LiveData<List<GenreListItem>> = _genreList

    private val _openFiltersCommand: SingleLiveEvent<AnimeSearchFilters> = SingleLiveEvent()
    val openFiltersCommand: SingleLiveEvent<AnimeSearchFilters> = _openFiltersCommand

    fun init() {
        loadGenres()
    }

    fun getCheckedGenres(): List<Genre> = genreList.value?.filter { it.isChecked }?.map { it.genre } ?: emptyList()

    fun onConfirmButton(filters: AnimeSearchFilters) {
        _openFiltersCommand.value = filters
    }

    private fun loadGenres() {
        viewModelScope.launch {
            try {
                _genreList.value = filterRepository.getGenreList().map { genre ->
                    GenreListItem(genre, false, ::onGenreListItemClick)
                }
            } catch (e: Exception) {
                processError(e)
            }
        }
    }

    private fun onGenreListItemClick(item: GenreListItem) {
        _genreList.value = _genreList.value?.map {
            if (it.genre == item.genre)
                it.copy(isChecked = !it.isChecked)
            else
                it
        }
    }
}