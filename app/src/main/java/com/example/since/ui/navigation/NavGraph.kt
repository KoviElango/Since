package com.example.since.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.since.ui.screens.AchievementArchiveScreen
import com.example.since.ui.screens.ActiveStreakScreen
import com.example.since.ui.screens.LobbyScreen
import com.example.since.viewmodel.MainViewModel

/**
 * AppNavGraph sets up navigation routes and links composable screens using the provided NavController.
 *
 * It acts as the central navigation graph for the application and defines screen transitions.
 *
 * @param navController The NavHostController that manages navigation state and backstack.
 * @param viewModel Shared MainViewModel for passing data/state to screens.
 * @param startDestination The initial screen route when the app launches.
 * @param modifier Optional modifier to apply to the NavHost.
 */

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
            LobbyScreen(
                viewModel = viewModel,
                onNavigateToActive = {
                    navController.navigate(Screen.ActiveStreak.route)
                },
                onNavigateToAchievements = {
                    navController.navigate(Screen.AchievementsArchive.route)
                }
            )
        }

        composable(Screen.ActiveStreak.route) {
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

        composable(Screen.AchievementsArchive.route) {
            AchievementArchiveScreen(viewModel = viewModel)
        }
    }
}
