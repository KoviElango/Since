package com.example.since.ui.navigation

sealed class Screen(val route: String) {
    object Lobby : Screen("lobby")
    object ActiveStreak : Screen("active_streak")
}
