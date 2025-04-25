package com.example.since.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.since.data.ClaimedAchievement
import com.example.since.data.SinceDatabase
import com.example.since.data.UserStreak
import com.example.since.data.repository.AchievementRepository
import com.example.since.data.repository.AchievementRepositoryImpl
import com.example.since.data.repository.StreakRepository
import com.example.since.data.repository.StreakRepositoryImpl
import com.example.since.domain.usecases.primaryusecase.AddOrReplaceStreakUseCase
import com.example.since.domain.usecases.primaryusecase.CalculatePersonalBestUseCase
import com.example.since.domain.usecases.primaryusecase.DeleteStreakUseCase
import com.example.since.domain.usecases.primaryusecase.GetActiveStreakUseCase
import com.example.since.domain.usecases.primaryusecase.GetRecentStreaksUseCase
import com.example.since.domain.usecases.primaryusecase.ResetStreakUseCase
import com.example.since.domain.usecases.primaryusecase.SetActiveStreakUseCase
import com.example.since.domain.usecases.primaryusecase.StartStreakTimerUseCase
import com.example.since.domain.usecases.primaryusecase.UpdateStreakDetailsUseCase
import com.example.since.domain.usecases.trophyusecases.ClaimAchievementUseCase
import com.example.since.domain.usecases.trophyusecases.GetAchievementsUseCase
import com.example.since.domain.usecases.widgetusecase.SaveStreakWidgetUseCase
import com.example.since.model.StreakDuration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * MainViewModel is the central state holder for the "Since" app.
 *
 * Bridges the presentation layer (UI) with the domain and data layers
 * using use cases and repositories to maintain separation of concerns.
 *
 * Responsibilities:
 * - Track habit streaks and timers
 * - Manage achievements (trophy room)
 * - Handle widget updates
 *
 * Architecture: Clean MVVM with UseCase delegation.
 */

class MainViewModel(application: Application) : AndroidViewModel(application) {


    // --- Repositories ---
    private val streakRepository: StreakRepository = StreakRepositoryImpl(SinceDatabase.getDatabase(application).streakDao())
    private val achievementRepository: AchievementRepository = AchievementRepositoryImpl(SinceDatabase.getDatabase(application).achievementDao())

    // --- UI State ---
    private val _streaks = MutableStateFlow<List<UserStreak>>(emptyList())
    val streaks: StateFlow<List<UserStreak>> = _streaks.asStateFlow()

    private val _timer = MutableStateFlow(StreakDuration(0, 0, 0, 0))
    val timer: StateFlow<StreakDuration> = _timer.asStateFlow()

    private val _activeStreak = MutableStateFlow<UserStreak?>(null)
    val activeStreak: StateFlow<UserStreak?> = _activeStreak.asStateFlow()

    private val _personalBest = MutableStateFlow(0L)

    private var timerJob: Job? = null

    // --- Streak Widget ---
    private val saveStreakWidgetUseCase = SaveStreakWidgetUseCase(getApplication())

    //UseCases for handling Streak logic (Lobby/Active screen)
    private val getRecentStreaksUseCase = GetRecentStreaksUseCase(streakRepository)
    private val calculatePersonalBestUseCase = CalculatePersonalBestUseCase()
    private val getActiveStreakUseCase = GetActiveStreakUseCase()
    private val resetStreakUseCase = ResetStreakUseCase()
    private val addOrReplaceStreakUseCase = AddOrReplaceStreakUseCase()
    private val startStreakTimerUseCase = StartStreakTimerUseCase()
    private val setActiveStreakUseCase = SetActiveStreakUseCase(streakRepository)
    private val updateStreakDetailsUseCase = UpdateStreakDetailsUseCase(streakRepository)
    private val deleteStreakUseCase = DeleteStreakUseCase(streakRepository)

    init {
        loadRecentStreaks()
    }

    /**
     * Load recent streaks from the repository and update local state.
     * Also detects the active streak and starts the timer if needed.
     */
    fun loadRecentStreaks() {
        viewModelScope.launch {

            val recent = getRecentStreaksUseCase()
            _streaks.value = recent

            val best = calculatePersonalBestUseCase(recent)
            _personalBest.value = best

            val active = getActiveStreakUseCase(recent)
            _activeStreak.value = active

            timerJob?.cancel()

            if (active != null) {
                startTickingTimer(active.resetTimestamp)
            } else {
                _timer.value = StreakDuration(0, 0, 0, 0)
            }
        }
    }

    /**
     * Start the ticking timer coroutine that updates UI every second,
     * recalculates duration, and updates widget & personal best if needed.
     */
    fun startTickingTimer(resetTime: Long) {
        startStreakTimerUseCase.start(
            scope = viewModelScope,
            resetTime = resetTime,
            currentStreakProvider = { _activeStreak.value },
            onTimerUpdate = { _timer.value = it },
            onStreakUpdated = {
                viewModelScope.launch {
                    streakRepository.updateStreak(it)
                    _activeStreak.value = it
                }
            },
            onTick = { saveStreakWidgetUseCase(resetTime) }
        )
    }

    /**
     * Add a new streak or replace an old one (if 3 are already present).
     * Clears any previous active streaks before insertion.
     */
    fun addOrReplaceStreak(newStreak: UserStreak) {
        viewModelScope.launch {
            streakRepository.clearActiveStreak()
            val existing = streakRepository.getRecentStreaks()
            val result = addOrReplaceStreakUseCase(newStreak, existing)

            if (existing.size < 3) {
                streakRepository.insertStreak(result)
            } else {
                streakRepository.updateStreak(result)
            }
            loadRecentStreaks()
        }
    }

    /**
     * Deletes a streak if it's not currently active.
     */
    fun deleteStreak(streak: UserStreak) {
        viewModelScope.launch {
            val deleted = deleteStreakUseCase(streak)
            if (deleted) {
                loadRecentStreaks()
            }
        }
    }

    /**
     * Resets a given streak, updates the personal best if needed,
     * and cancels the timer job.
     */
    fun resetStreak(current: UserStreak) {
        val updated = resetStreakUseCase(current, System.currentTimeMillis())

        viewModelScope.launch {
            streakRepository.updateStreak(updated)
            _activeStreak.value = null
            startStreakTimerUseCase.stop()
            _timer.value = StreakDuration(0, 0, 0, 0)

            loadRecentStreaks()

        }
    }

    /**
     * Update the name and reset clause for a given streak by ID.
     * This also refreshes the recent streak list and UI state.
     */
    fun updateStreakDetails(id: Int, name: String, clause: String) {
        viewModelScope.launch {
            val updated = updateStreakDetailsUseCase(id, name, clause)
            if (updated != null) {
                _activeStreak.value = updated
                loadRecentStreaks()
            }
        }
    }

    /**
     * Marks a streak as active, resets the timestamp,
     * and loads it into the active state.
     */
    fun setActiveStreak(streak: UserStreak) {
        viewModelScope.launch {
            setActiveStreakUseCase(streak)
            loadRecentStreaks()
        }
    }

    //Functions related to Trophy Room - Achievement

    private val claimAchievementUseCase = ClaimAchievementUseCase(achievementRepository)
    private val getAchievementsUseCase = GetAchievementsUseCase(achievementRepository)

    /**
     * Claims an achievement from the given streak and optional reflection.
     * Saves it to the Trophy Room for viewing later.
     */
    fun claimAchievement(streak: UserStreak, message: String?) {
        viewModelScope.launch {
            claimAchievementUseCase(streak, message)
        }
    }

    /**
     * Retrieves all achievements (claimed streaks) from repository.
     * Exposed as a Flow for reactive UI display.
     */
    fun getAchievements(): Flow<List<ClaimedAchievement>> {
        return getAchievementsUseCase()
    }
}
