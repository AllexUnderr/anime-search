package com.example.animesearch.search.model

import com.example.animesearch.filter.model.Filter

enum class OrderBy : Filter {
    RANK,
    POPULARITY,
    SCORE,
    END_DATE,
}