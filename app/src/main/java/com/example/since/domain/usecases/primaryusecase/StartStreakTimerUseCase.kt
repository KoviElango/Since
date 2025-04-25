package com.example.since.domain.usecases.primaryusecase

import com.example.since.data.UserStreak
import com.example.since.model.StreakCalculator
import com.example.since.model.StreakDuration
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

class StartStreakTimerUseCase {

    private var timerJob: Job? = null

    fun start(
        scope: CoroutineScope,
        resetTime: Long,
        currentStreakProvider: () -> UserStreak?,
        onTimerUpdate: (StreakDuration) -> Unit,
        onStreakUpdated: (UserStreak) -> Unit,
        onTick: (Long) -> Unit,
    ) {
        timerJob?.cancel()

        timerJob = scope.launch {
            while (isActive) {
                val now = System.currentTimeMillis()
                val elapsed = now - resetTime

                onTimerUpdate(StreakCalculator.getDetailedDurationSince(resetTime))
                onTick(resetTime)

                val current = currentStreakProvider()
                if (current != null && elapsed > current.personalBest) {
                    val updated = current.copy(personalBest = elapsed)
                    onStreakUpdated(updated)
                }

                delay(1000)
            }
        }
    }

    fun stop() {
        timerJob?.cancel()
    }
}
