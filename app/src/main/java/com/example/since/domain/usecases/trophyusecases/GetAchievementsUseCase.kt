package com.example.since.domain.usecases.trophyusecases

import com.example.since.data.ClaimedAchievement
import com.example.since.data.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow

class GetAchievementsUseCase(
    private val repository: AchievementRepository
) {
    operator fun invoke(): Flow<List<ClaimedAchievement>> {
        return repository.getAllAchievements()
    }
}
