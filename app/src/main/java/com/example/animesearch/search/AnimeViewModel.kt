package com.example.animesearch.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animesearch.filter.model.Filter
import com.example.animesearch.search.model.Anime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AnimeViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _animes: MutableLiveData<List<Anime>> = MutableLiveData()
    val animes: LiveData<List<Anime>> = _animes

    private val disposable: MutableList<Disposable> = mutableListOf()

    fun loadTopAnime() {
        disposable += animeRepository.getAnimeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _animes.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun loadFilteredAnimes(filters: List<Filter>) {
        disposable += animeRepository.getFilteredAnimeList(filters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _animes.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun destroy() {
        clearDisposable()
    }

    private fun clearDisposable() {
        disposable.forEach { it.dispose() }
        disposable.clear()
    }
}