package com.example.animesearch.search.model

import com.example.animesearch.R

enum class OrderBy(override val localizedStringRes: Int) : LocalizableItem {
    RANK(R.string.order_by_rank),
    POPULARITY(R.string.order_by_popularity),
    SCORE(R.string.order_by_score),
    END_DATE(R.string.order_by_end_date),
}