package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak

class GetActiveStreakUseCase {
    operator fun invoke(streaks: List<UserStreak>): UserStreak? {
        return streaks.find { it.isActive }
    }
}