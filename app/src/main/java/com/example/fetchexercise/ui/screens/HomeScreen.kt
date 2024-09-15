package com.example.fetchexercise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.fetchexercise.model.FetchModel
import com.example.fetchexercise.ui.viewmodel.FetchUiState

@Composable
fun HomeScreen(
    fetchUiState: FetchUiState, modifier: Modifier = Modifier
) {
    when (fetchUiState) {
        is FetchUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is FetchUiState.Success -> ResultScreen(fetchUiState.data, modifier.fillMaxSize())
        is FetchUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(10.dp),
        text = "Loading...",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun ResultScreen(data: Map<Int, List<FetchModel>>, modifier: Modifier = Modifier) {
    val isDarkMode = isSystemInDarkTheme()
    val dividerColor = if (isDarkMode) Color.LightGray else Color.Black

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = "Loaded ${data.size} lists with ${data.values.flatten().size} valid items",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        LazyColumn {
            items(data.entries.toList()) { entry ->
                Text(
                    buildAnnotatedString {
                        withStyle(style = MaterialTheme.typography.headlineSmall.toSpanStyle()) {
                            append("List ${entry.key} ")
                        }
                        withStyle(style = MaterialTheme.typography.bodySmall.toSpanStyle()) {
                            append("${entry.value.size} items")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                )
                HorizontalDivider(color = dividerColor, thickness = 2.dp)
                LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                    itemsIndexed(entry.value) { index, item ->
                        val backgroundColor = if (index % 2 != 0) {
                            Color.Unspecified
                        } else {
                            if (isDarkMode) Color.DarkGray else Color.LightGray
                        }
                        Box(
                            modifier = Modifier
                                .background(backgroundColor)
                                .padding(10.dp)
                                .fillMaxWidth(),
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = item.name.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        if (index < entry.value.size - 1) {
                            HorizontalDivider(color = dividerColor, thickness = 1.dp)
                        }
                    }
                }
                HorizontalDivider(color = dividerColor, thickness = 2.dp)
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(10.dp),
        text = "Error, could not load data",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
    )
}

