package com.example.since.data

import androidx.room.*

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
