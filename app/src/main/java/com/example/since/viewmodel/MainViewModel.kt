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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val streakRepository: StreakRepository = StreakRepositoryImpl(SinceDatabase.getDatabase(application).streakDao())
    private val achievementRepository: AchievementRepository = AchievementRepositoryImpl(SinceDatabase.getDatabase(application).achievementDao())


    private val _streaks = MutableStateFlow<List<UserStreak>>(emptyList())
    val streaks: StateFlow<List<UserStreak>> = _streaks.asStateFlow()

    private val _timer = MutableStateFlow(StreakDuration(0, 0, 0, 0))
    val timer: StateFlow<StreakDuration> = _timer.asStateFlow()

    private val _activeStreak = MutableStateFlow<UserStreak?>(null)
    val activeStreak: StateFlow<UserStreak?> = _activeStreak.asStateFlow()

    private val _personalBest = MutableStateFlow(0L)

    private var timerJob: Job? = null

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

    fun deleteStreak(streak: UserStreak) {
        viewModelScope.launch {
            val deleted = deleteStreakUseCase(streak)
            if (deleted) {
                loadRecentStreaks()
            }
        }
    }

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

    fun updateStreakDetails(id: Int, name: String, clause: String) {
        viewModelScope.launch {
            val updated = updateStreakDetailsUseCase(id, name, clause)
            if (updated != null) {
                _activeStreak.value = updated
                loadRecentStreaks()
            }
        }
    }

    fun setActiveStreak(streak: UserStreak) {
        viewModelScope.launch {
            setActiveStreakUseCase(streak)
            loadRecentStreaks()
        }
    }

    /*
    Functions related to Streak Widget
     */
    private val saveStreakWidgetUseCase = SaveStreakWidgetUseCase(getApplication())

    /*
    Functions related to Trophy Room - Achievement
     */

    private val claimAchievementUseCase = ClaimAchievementUseCase(achievementRepository)
    private val getAchievementsUseCase = GetAchievementsUseCase(achievementRepository)

    fun claimAchievement(streak: UserStreak, message: String?) {
        viewModelScope.launch {
            claimAchievementUseCase(streak, message)
        }
    }

    fun getAchievements(): Flow<List<ClaimedAchievement>> {
        return getAchievementsUseCase()
    }
}
