package com.example.since

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.since.ui.navigation.AppNavGraph
import com.example.since.ui.screens.ActiveStreakScreen
import com.example.since.ui.theme.SinceTheme
import com.example.since.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SinceTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()
                AppNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
