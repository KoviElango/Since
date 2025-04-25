package com.example.since.data.dao

import androidx.room.*
import com.example.since.data.UserStreak

/**
 * DAO interface for accessing and managing habit streaks in the Room database.
 *
 * Provides insert, update, delete, and query operations for the [UserStreak] entity.
 */
@Dao
interface StreakDao {

    @Insert
    suspend fun insertStreak(streak: UserStreak)

    @Update
    suspend fun updateStreak(streak: UserStreak)

    @Delete
    suspend fun deleteStreak(streak: UserStreak)

    @Query("SELECT * FROM streaks ORDER BY resetTimestamp DESC LIMIT 3")
    suspend fun getRecentStreaks(): List<UserStreak>

    @Query("UPDATE streaks SET isActive = 0")
    suspend fun clearActiveStreak()

    @Query("SELECT * FROM streaks WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveStreak(): UserStreak?

    @Query("SELECT * FROM streaks WHERE id = :id LIMIT 1")
    suspend fun getStreakById(id: Int): UserStreak?

}
