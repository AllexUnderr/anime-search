package com.example.animesearch.module

import android.content.Context
import com.example.animesearch.filter.FilterApi
import com.example.animesearch.filter.FilterRepository
import com.example.animesearch.filter.FilterViewModel
import com.example.animesearch.filter.model.database.GenreDao
import com.example.animesearch.helper.AppDatabase
import com.example.animesearch.search.SearchApi
import com.example.animesearch.search.SearchRepository
import com.example.animesearch.search.SearchViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    val url = "https://api.jikan.moe/v4/"

    val interceptor = HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

    val client = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()

    val json = Json { ignoreUnknownKeys = true }
    val contentType = "application/json".toMediaType()

    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .baseUrl(url)
            .build()

    fun provideAnimeApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

    fun provideFilterApi(retrofit: Retrofit): FilterApi = retrofit.create(FilterApi::class.java)

    fun provideGenreDao(context: Context): GenreDao = AppDatabase.getInstance(context).getGenreDao()

    single<Retrofit> { provideRetrofit() }
    single<SearchApi> { provideAnimeApi(get()) }
    single<FilterApi> { provideFilterApi(get()) }
    single { provideGenreDao(get()) }

    singleOf(::SearchRepository)
    singleOf(::FilterRepository)

    viewModelOf(::FilterViewModel)
    viewModelOf(::SearchViewModel)
}