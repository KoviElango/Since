package com.example.since.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.since.ui.screens.ActiveStreakScreen
import com.example.since.ui.screens.LobbyScreen
import com.example.since.viewmodel.MainViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Lobby.route) {
            Log.d("NavGraph", "LobbyScreen composable rendered")
            LobbyScreen(
                viewModel = viewModel,
                onNavigateToActive = {
                    navController.navigate(Screen.ActiveStreak.route)
                }
            )
        }

        composable(Screen.ActiveStreak.route) {
            Log.d("NavGraph", "ActiveStreakScreen composable rendered")
            ActiveStreakScreen(
                viewModel = viewModel,
                navController = navController,
                onResetComplete = {
                    navController.navigate(Screen.Lobby.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
