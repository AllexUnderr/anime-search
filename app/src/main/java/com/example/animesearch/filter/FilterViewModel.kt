package com.example.animesearch.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animesearch.filter.model.Filter
import com.example.animesearch.filter.model.Genre
import com.example.animesearch.helper.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class FilterViewModel(private val filterRepository: FilterRepository) {
    private val _genres: MutableLiveData<List<Genre>> = MutableLiveData()
    val genres: LiveData<List<Genre>> = _genres

    val checkedGenres: MutableList<Genre> = mutableListOf()
    val genreCheckListener = object : GenreRecyclerAdapter.OnGenreCheckListener {
        override fun onGenreCheck(genre: Genre) {
            checkedGenres.add(genre)
        }

        override fun onGenreUncheck(genre: Genre) {
            checkedGenres.add(genre)
        }
    }

    val passFiltersCommand = SingleLiveEvent<List<Filter>>()

    private val disposable: MutableList<Disposable> = mutableListOf()

    fun loadGenres() {
        disposable += filterRepository.getGenreList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _genres.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun passFilters(
        type: Filter,
        minScore: Filter,
        genres: List<Filter>,
        status: Filter,
        orderBy: Filter
    ) {
        passFiltersCommand.value = listOf(type, minScore, *genres.toTypedArray(), status, orderBy)
    }

    fun destroy() {
        clearDisposable()
    }

    private fun clearDisposable() {
        disposable.forEach { it.dispose() }
        disposable.clear()
    }
}