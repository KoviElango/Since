package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak
import com.example.since.data.repository.StreakRepository

class DeleteStreakUseCase(
    private val repository: StreakRepository
) {
    suspend operator fun invoke(streak: UserStreak): Boolean {
        return if (!streak.isActive) {
            repository.deleteStreak(streak)
            true
        } else {
            false
        }
    }
}