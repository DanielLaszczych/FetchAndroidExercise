package com.example.fetchexercise.model

import kotlinx.serialization.Serializable

@Serializable
/**
 * A data class to model the keys and data types from the JSON file
 * which will allows us to convert the values from the JSON file
 * into a FetchModel object
 */
data class FetchModel2(
    val id: Int, val listId: Int, val name: String?, val liked: Boolean
)