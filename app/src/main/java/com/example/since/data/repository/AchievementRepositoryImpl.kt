package com.example.since.data.repository

import com.example.since.data.ClaimedAchievement
import com.example.since.data.dao.AchievementDao
import kotlinx.coroutines.flow.Flow

class AchievementRepositoryImpl(
    private val dao: AchievementDao
) : AchievementRepository {
    override suspend fun insertAchievement(achievement: ClaimedAchievement) {
        dao.insertAchievement(achievement)
    }

    override fun getAllAchievements(): Flow<List<ClaimedAchievement>> {
        return dao.getAllAchievements()
    }
}
