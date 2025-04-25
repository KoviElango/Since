package com.example.since.data.repository

import com.example.since.data.UserStreak

interface StreakRepository {
    suspend fun getRecentStreaks(): List<UserStreak>
    suspend fun getActiveStreak(): UserStreak?
    suspend fun getStreakById(id: Int): UserStreak?
    suspend fun insertStreak(streak: UserStreak)
    suspend fun updateStreak(streak: UserStreak)
    suspend fun deleteStreak(streak: UserStreak)
    suspend fun clearActiveStreak()
}
