package com.example.animesearch.module

import com.example.animesearch.helper.RetrofitProvider
import com.example.animesearch.search.AnimeApi
import com.example.animesearch.search.AnimeRepository
import com.example.animesearch.search.AnimeViewModel
import dagger.Module
import dagger.Provides

@Module
class AnimeModule {

    @Provides
    fun provideAnimeApi(): AnimeApi = RetrofitProvider.retrofit.create(AnimeApi::class.java)

    @Provides
    fun provideAnimeRepository(animeApi: AnimeApi): AnimeRepository = AnimeRepository(animeApi)

    @Provides
    fun provideAnimeViewModel(animeRepository: AnimeRepository): AnimeViewModel =
        AnimeViewModel(animeRepository)
}