package com.example.fetchexercise.ui.screens

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.fetchexercise.model.FetchModel2
import com.example.fetchexercise.ui.viewmodel.FetchUiState

/**
 * To main screen of our application that will dynamically render
 * a screen based on our fetch state
 */
@Composable
fun HomeScreen(
    fetchUiState: FetchUiState, likeItem: (Int, Int) -> Unit, modifier: Modifier = Modifier
) {
    when (fetchUiState) {
        is FetchUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is FetchUiState.Success -> ResultScreen(fetchUiState.data, likeItem, modifier.fillMaxSize())
        is FetchUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

/**
 * Screen that will be displayed when we are waiting for our fetch request
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(10.dp),
        text = "Loading...",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
    )
}

/**
 * Screen that will display our fetched results by using lazy columns to
 * save resources by only displaying what is visible on the screen
 */
@Composable
fun ResultScreen(data: Map<Int, List<FetchModel2>>, likeItem: (Int, Int) -> Unit, modifier: Modifier = Modifier) {
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
                        Row(
                            modifier = Modifier
                                .clickable {
                                    likeItem(entry.key, item.id)
                                }
                                .background(backgroundColor)
                                .padding(10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = item.name.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if (item.liked) {
                                Icon(
                                    Icons.Sharp.Favorite,
                                    contentDescription = "Liked",
                                    Modifier
                                        .padding(start = 10.dp)
                                        .size(15.dp)
                                )
                            }
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

/**
 * Screen that will be displayed when there was error with our request
 * such as lack of connection
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(10.dp),
        text = "Error, could not load data",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
    )
}

