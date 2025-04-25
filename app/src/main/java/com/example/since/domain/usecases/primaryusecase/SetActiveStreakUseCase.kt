package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak
import com.example.since.data.repository.StreakRepository

class SetActiveStreakUseCase(
    private val repository: StreakRepository
) {
    suspend operator fun invoke(streak: UserStreak): UserStreak {
        repository.clearActiveStreak()
        val updated = streak.copy(
            resetTimestamp = System.currentTimeMillis(),
            isActive = true
        )
        repository.updateStreak(updated)
        return updated
    }
}
