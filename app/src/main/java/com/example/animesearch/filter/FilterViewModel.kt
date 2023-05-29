package com.example.animesearch.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.filter.model.GenreListItem
import com.example.animesearch.helper.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class FilterViewModel(private val filterRepository: FilterRepository) {
    private val _genreList: MutableLiveData<List<GenreListItem>> = MutableLiveData()
    val genreList: LiveData<List<GenreListItem>> = _genreList

    val passFiltersCommand = SingleLiveEvent<AnimeSearchFilters>()

    private val disposable: MutableList<Disposable> = mutableListOf()

    fun init() {
        loadGenres()
    }

    fun loadGenres() {
        disposable += filterRepository.getGenreList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _genreList.value = it.map { genre -> GenreListItem(genre, false, ::onGenreListItemClick) }
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun getCheckedGenres() =
        if (genreList.value?.filter { it.isChecked }?.size != 0)
            genreList.value?.filter { it.isChecked }?.map { it.genre }
        else
            null

    fun passFilters(filters: AnimeSearchFilters) {
        passFiltersCommand.value = filters
    }

    fun destroy() {
        clearDisposable()
    }

    private fun onGenreListItemClick(item: GenreListItem) {
        _genreList.value = _genreList.value?.map {
            if (it.genre == item.genre)
                it.copy(isChecked = !it.isChecked)
            else
                it
        }
    }

    private fun clearDisposable() {
        disposable.forEach { it.dispose() }
        disposable.clear()
    }
}