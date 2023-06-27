package com.example.animesearch.search.model

import com.example.animesearch.R

enum class AnimeStatus(override val localizedStringRes: Int) : LocalizableItem {
    AIRING(R.string.anime_status_airing),
    COMPLETE(R.string.anime_status_complete),
    UPCOMING(R.string.anime_status_upcoming),
}