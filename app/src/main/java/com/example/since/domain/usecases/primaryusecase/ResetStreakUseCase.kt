package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak

class ResetStreakUseCase {
    operator fun invoke(current: UserStreak, currentTime: Long): UserStreak {
        val elapsed = currentTime - current.resetTimestamp
        return current.copy(
            resetTimestamp = currentTime,
            personalBest = maxOf(current.personalBest, elapsed),
            isActive = false
        )
    }
}