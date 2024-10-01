package com.example.fetchexercise.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fetchexercise.FetchApplication
import com.example.fetchexercise.data.FetchRepository
import com.example.fetchexercise.model.FetchModel2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * Establish our different possible states
 */
sealed interface FetchUiState {
    data class Success(val data: Map<Int, List<FetchModel2>>) : FetchUiState
    data object Error : FetchUiState
    data object Loading : FetchUiState
}

class FetchViewModel(private val fetchRepository: FetchRepository) : ViewModel() {
    private val _fetchUiState = MutableStateFlow<FetchUiState>(FetchUiState.Loading)
    val fetchUiState: StateFlow<FetchUiState> = _fetchUiState

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
            _fetchUiState.value = FetchUiState.Loading
            _fetchUiState.value = try {
                val result = fetchRepository.getFetchItems().map { item ->
                    FetchModel2(item.id, item.listId, item.name, false)
                }
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

    fun likeItem(listId: Int, id: Int) {
        val currentState = _fetchUiState.value
        if (currentState is FetchUiState.Success) {
            val data = currentState.data
            val list = data[listId] ?: return

            val updatedList = list.map { item ->
                if (item.id == id) {
                    item.copy(liked = !item.liked)
                } else {
                    item
                }
            }

            val newMap = data.toMutableMap().apply {
                this[listId] = updatedList
            }

            _fetchUiState.value = FetchUiState.Success(newMap)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FetchApplication)
                val fetchRepository = application.container.fetchRepository
                FetchViewModel(fetchRepository = fetchRepository)
            }
        }
    }
}