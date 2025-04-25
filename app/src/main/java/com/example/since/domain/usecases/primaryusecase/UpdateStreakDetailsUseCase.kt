package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak
import com.example.since.data.repository.StreakRepository

class UpdateStreakDetailsUseCase(
    private val repository: StreakRepository
) {
    suspend operator fun invoke(id: Int, name: String, clause: String): UserStreak? {
        val current = repository.getStreakById(id)
        return if (current != null) {
            val updated = current.copy(name = name, resetClause = clause)
            repository.updateStreak(updated)
            updated
        } else {
            null
        }
    }
}
