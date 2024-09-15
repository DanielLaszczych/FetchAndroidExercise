package com.example.fetchexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.fetchexercise.ui.FetchApp
import com.example.fetchexercise.ui.theme.FetchExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchExerciseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FetchApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}