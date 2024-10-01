package com.example.fetchexercise.network

import com.example.fetchexercise.model.FetchModel
import retrofit2.http.GET

/**
 * Interface for any API calls we want to create
 */
interface FetchApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<FetchModel>
}