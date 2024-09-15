package com.example.fetchexercise.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchexercise.network.FetchApi
import com.example.fetchexercise.model.FetchModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * Establish our different possible states
 */
sealed interface FetchUiState {
    data class Success(val data: Map<Int, List<FetchModel>>) : FetchUiState
    data object Error : FetchUiState
    data object Loading : FetchUiState
}

class FetchViewModel : ViewModel() {
    var fetchUiState: FetchUiState by mutableStateOf(FetchUiState.Loading)
        private set

    /**
     * Make a call to fetch the JSON file when our view model is created
     */
    init {
        getItems()
    }

    /**
     * Use the retrofit service to make the GET request and then
     * process the result by filtering names that are blank or null,
     * grouping by listId, and sorting by listId and then name
     */
    private fun getItems() {
        viewModelScope.launch {
            fetchUiState = FetchUiState.Loading
            fetchUiState = try {
                val result = FetchApi.retrofitService.getItems()
                val processedResult = result
                    .filter { !it.name.isNullOrBlank() }
                    .groupBy { it.listId }
                    .toSortedMap()
                    .mapValues { (_, groupedItems) ->
                        groupedItems.sortedBy { it.name?.substring(5)?.toInt() }
                    }
                FetchUiState.Success(
                    processedResult
                )
            } catch (e: IOException) {
                FetchUiState.Error
            } catch (e: HttpException) {
                FetchUiState.Error
            }
        }
    }
}