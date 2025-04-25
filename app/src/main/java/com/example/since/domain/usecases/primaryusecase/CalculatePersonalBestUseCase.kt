package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak

class CalculatePersonalBestUseCase {
    operator fun invoke(streaks: List<UserStreak>): Long {
        return streaks.maxOfOrNull { it.personalBest } ?: 0L
    }
}
