package com.example.animesearch

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val url = "https://api.jikan.moe/v4/"

    private val logging =
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .build()

    val retrofit: Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(url)
        .build()
}