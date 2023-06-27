package com.example.animesearch.search.model

import com.example.animesearch.R

enum class AnimeType(override val localizedStringRes: Int) : LocalizableItem {
    TV(R.string.anime_type_tv),
    MOVIE(R.string.anime_type_movie),
    OVA(R.string.anime_type_ova),
    SPECIAL(R.string.anime_type_special),
    ONA(R.string.anime_type_ona),
    MUSIC(R.string.anime_type_music),
}