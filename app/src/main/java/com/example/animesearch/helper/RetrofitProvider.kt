package com.example.animesearch.helper

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object RetrofitProvider {

    private const val url = "https://api.jikan.moe/v4/"

    private val interceptor =
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()

    private val json = Json { ignoreUnknownKeys = true }
    private val contentType = "application/json".toMediaType()

    val retrofit: Retrofit = Retrofit
        .Builder()
        .addConverterFactory(json.asConverterFactory(contentType))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .baseUrl(url)
        .build()
}