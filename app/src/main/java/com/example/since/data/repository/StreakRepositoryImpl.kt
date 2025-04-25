package com.example.since.data.repository

import com.example.since.data.UserStreak
import com.example.since.data.dao.StreakDao

class StreakRepositoryImpl(
    private val dao: StreakDao
) : StreakRepository {

    override suspend fun getRecentStreaks(): List<UserStreak> = dao.getRecentStreaks()
    override suspend fun getActiveStreak(): UserStreak? = dao.getActiveStreak()
    override suspend fun getStreakById(id: Int): UserStreak? = dao.getStreakById(id)
    override suspend fun insertStreak(streak: UserStreak) = dao.insertStreak(streak)
    override suspend fun updateStreak(streak: UserStreak) = dao.updateStreak(streak)
    override suspend fun deleteStreak(streak: UserStreak) = dao.deleteStreak(streak)
    override suspend fun clearActiveStreak() = dao.clearActiveStreak()

}
