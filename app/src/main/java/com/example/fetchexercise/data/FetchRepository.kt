package com.example.fetchexercise.data

import com.example.fetchexercise.model.FetchModel
import com.example.fetchexercise.network.FetchApiService

interface FetchRepository {
    suspend fun getFetchItems(): List<FetchModel>
}

class NetworkFetchRepository(
    private val fetchApiService: FetchApiService
) : FetchRepository {
    override suspend fun getFetchItems(): List<FetchModel> = fetchApiService.getItems()
}