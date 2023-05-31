package com.example.animesearch.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.animesearch.core.BaseViewModel
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.model.Anime
import kotlinx.coroutines.launch

class AnimeViewModel(private val animeRepository: AnimeRepository) : BaseViewModel() {

    private val _animes: MutableLiveData<List<Anime>> = MutableLiveData()
    val animes: LiveData<List<Anime>> = _animes

    fun loadTopAnime() {
        viewModelScope.launch {
            try {
                _animes.value = animeRepository.getAnimeList()
            } catch (e: Exception) {
                processError(e)
            }
        }
    }

    fun loadFilteredAnimes(filters: AnimeSearchFilters) {
        viewModelScope.launch {
            try {
                _animes.value = animeRepository.getFilteredAnimeList(filters)
            } catch (e: Exception) {
                processError(e)
            }
        }
    }
}