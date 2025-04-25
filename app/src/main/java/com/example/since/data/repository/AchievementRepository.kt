package com.example.since.data.repository

import com.example.since.data.ClaimedAchievement
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
    suspend fun insertAchievement(achievement: ClaimedAchievement)
    fun getAllAchievements(): Flow<List<ClaimedAchievement>>
}
