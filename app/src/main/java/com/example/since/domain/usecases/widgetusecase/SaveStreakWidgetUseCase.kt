package com.example.since.domain.usecases.widgetusecase

import com.example.since.data.repository.WidgetRepository

class SaveStreakWidgetUseCase(
    private val widgetRepository: WidgetRepository
) {
    operator fun invoke(resetTimestamp: Long, isActive: Boolean) {
        widgetRepository.saveResetTimestamp(resetTimestamp)
        widgetRepository.saveIsActive(isActive)
    }
}
