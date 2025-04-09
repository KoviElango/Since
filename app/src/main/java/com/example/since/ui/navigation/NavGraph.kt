package com.example.since.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.since.ui.screens.ActiveStreakScreen
import com.example.since.ui.screens.LobbyScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.Lobby.route, modifier = modifier) {
        composable(Screen.Lobby.route) {
            LobbyScreen(onNavigateToActive = {
                navController.navigate(Screen.ActiveStreak.route)
            })
        }
        composable(Screen.ActiveStreak.route) {
            ActiveStreakScreen()
        }
    }
}
