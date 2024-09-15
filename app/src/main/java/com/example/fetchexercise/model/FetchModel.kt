package com.example.fetchexercise.model

import kotlinx.serialization.Serializable

@Serializable
data class FetchModel(
    val id: Int, val listId: Int, val name: String?
)