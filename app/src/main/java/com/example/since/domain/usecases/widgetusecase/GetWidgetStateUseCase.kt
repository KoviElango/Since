package com.example.since.domain.usecases.widgetusecase

import com.example.since.data.repository.WidgetRepository
import com.example.since.model.StreakWidgetState

class GetWidgetStateUseCase(
    private val widgetRepository: WidgetRepository
) {
    private val MILLIS_IN_A_DAY = 86_400_000L

    fun invoke(): StreakWidgetState {
        val isActive = widgetRepository.getIsActive()
        val resetTimestamp = widgetRepository.getResetTimestamp()

        return if (isActive) {
            val days = (System.currentTimeMillis() - resetTimestamp) / MILLIS_IN_A_DAY
            StreakWidgetState.Active(days)
        } else {
            StreakWidgetState.NoActiveStreak
        }
    }
}
