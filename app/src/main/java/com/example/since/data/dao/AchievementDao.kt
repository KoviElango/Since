package com.example.since.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.since.data.ClaimedAchievement
import kotlinx.coroutines.flow.Flow

/**
 * DAO interface for managing claimed achievements in the Room database.
 *
 * Provides methods for inserting and querying [ClaimedAchievement] entries.
 */
@Dao
interface AchievementDao {

    @Insert
    suspend fun insertAchievement(achievement: ClaimedAchievement)

    @Query("SELECT * FROM achievements ORDER BY datetimeClaimed DESC")
    fun getAllAchievements(): Flow<List<ClaimedAchievement>>
}
