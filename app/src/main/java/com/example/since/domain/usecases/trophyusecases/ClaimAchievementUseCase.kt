package com.example.since.domain.usecases.trophyusecases

import com.example.since.data.ClaimedAchievement
import com.example.since.data.UserStreak
import com.example.since.data.repository.AchievementRepository

class ClaimAchievementUseCase(
    private val repository: AchievementRepository
) {
    suspend operator fun invoke(streak: UserStreak, message: String?) {
        val claimed = ClaimedAchievement(
            name = streak.name,
            resetClause = streak.resetClause,
            datetimeClaimed = System.currentTimeMillis(),
            streakLengthDays = (System.currentTimeMillis() - streak.resetTimestamp) / (1000 * 60 * 60 * 24),
            userMessage = message
        )
        repository.insertAchievement(claimed)
    }
}