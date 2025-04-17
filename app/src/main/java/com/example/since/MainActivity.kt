package com.example.since

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.since.ui.navigation.AppNavGraph
import com.example.since.ui.navigation.Screen
import com.example.since.ui.theme.SinceTheme
import com.example.since.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SinceTheme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()
                val activeStreakState = viewModel.activeStreak.collectAsState()

                val startDestination = remember(activeStreakState.value) {
                    if (activeStreakState.value != null) Screen.ActiveStreak.route else Screen.Lobby.route
                }

                AppNavGraph(
                    navController = navController,
                    viewModel = viewModel,
                    startDestination = startDestination
                )
            }
        }
    }
}

