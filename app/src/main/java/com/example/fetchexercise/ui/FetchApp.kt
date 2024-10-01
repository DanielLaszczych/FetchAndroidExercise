package com.example.fetchexercise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchexercise.ui.viewmodel.FetchViewModel
import com.example.fetchexercise.ui.screens.HomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FetchApp(modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color(0xFFFBA919), darkIcons = false)
    val fetchViewModel: FetchViewModel = viewModel(factory = FetchViewModel.Factory)
    val fetchUiState = fetchViewModel.fetchUiState.collectAsState().value

    Column(modifier = modifier) {
        Text(
            text = "Fetch Exercise",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier
                .background(
                    Color(0xFFFBA919)
                )
                .fillMaxWidth()
                .padding(10.dp)
        )
        HomeScreen(fetchUiState, fetchViewModel::likeItem)
    }
}