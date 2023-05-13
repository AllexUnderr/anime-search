package com.example.animesearch.module

import android.content.Context
import com.example.animesearch.filter.FilterApi
import com.example.animesearch.filter.FilterRepository
import com.example.animesearch.filter.FilterViewModel
import com.example.animesearch.filter.model.database.GenreDao
import com.example.animesearch.helper.AppDatabase
import com.example.animesearch.helper.RetrofitProvider
import dagger.Module
import dagger.Provides

@Module
class FilterModule {

    @Provides
    fun provideFilterApi(): FilterApi = RetrofitProvider.retrofit.create(FilterApi::class.java)

    @Provides
    fun provideGenreDao(context: Context): GenreDao = AppDatabase.getInstance(context).getGenreDao()

    @Provides
    fun provideFilterRepository(filterApi: FilterApi, genreDao: GenreDao): FilterRepository =
        FilterRepository(filterApi, genreDao)

    @Provides
    fun provideFilterViewModel(filterRepository: FilterRepository): FilterViewModel =
        FilterViewModel(filterRepository)
}