package com.example.fetchexercise.data

import com.example.fetchexercise.network.FetchApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val fetchRepository: FetchRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://fetch-hiring.s3.amazonaws.com"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: FetchApiService by lazy {
        retrofit.create(FetchApiService::class.java)
    }

    override val fetchRepository: FetchRepository by lazy {
        NetworkFetchRepository(retrofitService)
    }
}