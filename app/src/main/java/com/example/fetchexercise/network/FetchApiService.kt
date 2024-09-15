package com.example.fetchexercise.network

import com.example.fetchexercise.model.FetchModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).build()

interface FetchApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<FetchModel>
}

object FetchApi {
    val retrofitService: FetchApiService by lazy {
        retrofit.create(FetchApiService::class.java)
    }
}