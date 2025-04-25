package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak
import com.example.since.data.repository.StreakRepository

class GetRecentStreaksUseCase(
    private val streakRepository: StreakRepository
) {
    suspend operator fun invoke(): List<UserStreak> {
        return streakRepository.getRecentStreaks()
    }
}