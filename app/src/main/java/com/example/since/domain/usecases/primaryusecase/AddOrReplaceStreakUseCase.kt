package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak

class AddOrReplaceStreakUseCase {
    operator fun invoke(newStreak: UserStreak, existing: List<UserStreak>): UserStreak {
        return if (existing.size < 3) {
            newStreak.copy(isActive = true)
        } else {
            val oldest = existing.minByOrNull { it.resetTimestamp }
            newStreak.copy(id = oldest?.id ?: 0, isActive = true)
        }
    }
}
