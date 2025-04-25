package com.example.since.ui.navigation

/**
 * Screen defines all possible routes used in the app for navigation.
 *
 * Each object represents a composable destination in the navigation graph.
 *
 * Use `Screen.route` when navigating via the NavController.
 */

sealed class Screen(val route: String) {
    object Lobby : Screen("lobby")
    object ActiveStreak : Screen("active_streak")
    object AchievementsArchive : Screen("achievements_archive")
}
