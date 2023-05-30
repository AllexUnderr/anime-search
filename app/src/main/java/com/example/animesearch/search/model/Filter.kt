package com.example.animesearch.search.model

import com.example.animesearch.R

sealed interface Filter

fun Filter.getStringResourceId(): Int {
    return when(this) {
        is AnimeStatus -> when(this) {
            AnimeStatus.AIRING -> R.string.anime_status_airing
            AnimeStatus.COMPLETE -> R.string.anime_status_complete
            AnimeStatus.UPCOMING -> R.string.anime_status_upcoming
        }
        is AnimeType -> when(this) {
            AnimeType.TV -> R.string.anime_type_tv
            AnimeType.MOVIE -> R.string.anime_type_movie
            AnimeType.OVA -> R.string.anime_type_ova
            AnimeType.SPECIAL -> R.string.anime_type_special
            AnimeType.ONA -> R.string.anime_type_ona
            AnimeType.MUSIC -> R.string.anime_type_music
        }
        is OrderBy -> when(this) {
            OrderBy.RANK -> R.string.order_by_rank
            OrderBy.POPULARITY -> R.string.order_by_popularity
            OrderBy.SCORE -> R.string.order_by_score
            OrderBy.END_DATE -> R.string.order_by_end_date
        }
    }
}