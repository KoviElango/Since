package com.example.since.model

sealed class StreakWidgetState {
    data class Active(val days: Long) : StreakWidgetState()
    object NoActiveStreak : StreakWidgetState()
}
