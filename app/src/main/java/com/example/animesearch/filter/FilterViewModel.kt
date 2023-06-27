package com.example.animesearch.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.animesearch.R
import com.example.animesearch.core.BaseViewModel
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.filter.model.Genre
import com.example.animesearch.filter.model.GenreListItem
import com.example.animesearch.filter.model.SelectableChip
import com.example.animesearch.helper.SingleLiveEvent
import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.LocalizableItem
import com.example.animesearch.search.model.OrderBy
import kotlinx.coroutines.launch

class FilterViewModel(
    private val filterRepository: FilterRepository,
) : BaseViewModel() {

    private val _genreList: MutableLiveData<List<GenreListItem>> = MutableLiveData()
    val genreList: LiveData<List<GenreListItem>> = _genreList

    private val _openAnimesCommand: SingleLiveEvent<AnimeSearchFilters> = SingleLiveEvent()
    val openAnimesCommand: SingleLiveEvent<AnimeSearchFilters> = _openAnimesCommand

    private val _typeChips: MutableLiveData<List<SelectableChip>> = MutableLiveData()
    val typeChips: LiveData<List<SelectableChip>> = _typeChips

    private val _statusChips: MutableLiveData<List<SelectableChip>> = MutableLiveData()
    val statusChips: LiveData<List<SelectableChip>> = _statusChips

    private val _orderByChips: MutableLiveData<List<SelectableChip>> = MutableLiveData()
    val orderByChips: LiveData<List<SelectableChip>> = _orderByChips

    private var minScore: Double? = null

    private var filters: AnimeSearchFilters = AnimeSearchFilters.Empty

    fun init() {
        _typeChips.value = AnimeType.values().map { SelectableChip(it, false) }.prefixedAny()
        _statusChips.value = AnimeStatus.values().map { SelectableChip(it, false) }.prefixedAny()
        _orderByChips.value = OrderBy.values().map { SelectableChip(it, false) }.prefixedAny()

        loadGenres()
    }

    fun onTypeClick(name: LocalizableItem) {
        _typeChips.value = _typeChips.value?.map { it.copy(isSelected = it.item == name) }
    }

    fun onStatusClick(name: LocalizableItem) {
        _statusChips.value = _statusChips.value?.map { it.copy(isSelected = it.item == name) }
    }

    fun onOrderByClick(name: LocalizableItem) {
        _orderByChips.value = _orderByChips.value?.map { it.copy(isSelected = it.item == name) }
    }

    fun onMinScoreChanged(newValue: String) {
        minScore = newValue.toDoubleOrNull()
    }

    fun onConfirmButton() {
        filters = AnimeSearchFilters(
            type = typeChips.value
                ?.first { it.isSelected }
                ?.let { it.item as? AnimeType },
            minScore = minScore,
            genres = getCheckedGenres(),
            status = statusChips.value
                ?.first { it.isSelected }
                ?.let { it.item as? AnimeStatus },
            orderBy = orderByChips.value
                ?.first { it.isSelected }
                ?.let { it.item as? OrderBy },
        )
        _openAnimesCommand.value = filters
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

    private fun getCheckedGenres(): List<Genre> =
        genreList.value?.filter { it.isChecked }?.map { it.genre } ?: emptyList()

    private fun List<SelectableChip>.prefixedAny(): List<SelectableChip> =
        listOf(
            SelectableChip(LocalizableAny, isSelected = true),
            *this.toTypedArray(),
        )

    private object LocalizableAny : LocalizableItem {
        override val localizedStringRes: Int = R.string.any
    }
}