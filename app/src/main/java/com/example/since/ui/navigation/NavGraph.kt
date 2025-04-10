package com.example.since.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.since.ui.screens.ActiveStreakScreen
import com.example.since.ui.screens.LobbyScreen
import com.example.since.viewmodel.MainViewModel

@Composable
fun AppNavGraph(navController: NavHostController,
                viewModel: MainViewModel,
                modifier: Modifier = Modifier)
{
    NavHost(navController = navController, startDestination = Screen.Lobby.route, modifier = modifier) {
        composable(Screen.Lobby.route) {
            LobbyScreen(
                viewModel = viewModel,
                onNavigateToActive = {
                navController.navigate(Screen.ActiveStreak.route)
            })
        }
        composable(Screen.ActiveStreak.route) {
            ActiveStreakScreen(viewModel = viewModel)
        }
    }
}
