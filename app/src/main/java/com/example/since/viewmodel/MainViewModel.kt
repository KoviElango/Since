package com.example.since.viewmodel

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.since.data.AchievementDao
import com.example.since.data.ClaimedAchievement
import com.example.since.data.SinceDatabase
import com.example.since.data.StreakDao
import com.example.since.data.UserStreak
import com.example.since.model.StreakCalculator
import com.example.since.model.StreakDuration
import com.example.since.widget.StreakWidget
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: StreakDao = SinceDatabase.getDatabase(application).streakDao()

    private val _streaks = MutableStateFlow<List<UserStreak>>(emptyList())
    val streaks: StateFlow<List<UserStreak>> = _streaks.asStateFlow()

    private val _timer = MutableStateFlow(StreakDuration(0, 0, 0, 0))
    val timer: StateFlow<StreakDuration> = _timer.asStateFlow()

    private val _activeStreak = MutableStateFlow<UserStreak?>(null)
    val activeStreak: StateFlow<UserStreak?> = _activeStreak.asStateFlow()

    private val _personalBest = MutableStateFlow(0L)

    private var timerJob: Job? = null

    private val achievementDao: AchievementDao = SinceDatabase.getDatabase(application).achievementDao()


    init {
        loadRecentStreaks()
    }

    fun loadRecentStreaks() {
        viewModelScope.launch {
            val recent = dao.getRecentStreaks()
            _streaks.value = recent

            val best = recent.maxOfOrNull { it.personalBest } ?: 0L
            _personalBest.value = best

            val active = dao.getActiveStreak()
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
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            while (true) {
                val now = System.currentTimeMillis()
                val elapsed = now - resetTime

                _timer.value = StreakCalculator.getDetailedDurationSince(resetTime)
                saveWidgetStreakToPrefs(resetTime)

                val current = _activeStreak.value
                if (current != null && elapsed > current.personalBest) {
                    val updated = current.copy(personalBest = elapsed)
                    dao.updateStreak(updated)
                    _activeStreak.value = updated

                    // Placeholder: Show "Congrats! New Personal Best" here
                    // e.g. triggerToast("New Personal Best!") or set a state flag
                }

                delay(1000)
            }
        }
    }

    fun addOrReplaceStreak(newStreak: UserStreak) {
        viewModelScope.launch {
            dao.clearActiveStreak()
            val existing = dao.getRecentStreaks()
            val newActive = newStreak.copy(isActive = true)

            if (existing.size < 3) {
                dao.insertStreak(newActive)
            } else {
                val oldest = existing.minByOrNull { it.resetTimestamp }
                if (oldest != null) {
                    val updated = newActive.copy(id = oldest.id)
                    dao.updateStreak(updated)
                }
            }

            loadRecentStreaks()
        }
    }

    fun deleteStreak(streak: UserStreak) {
        viewModelScope.launch {
            if (streak.isActive) return@launch
            dao.deleteStreak(streak)
            loadRecentStreaks()
        }
    }

    fun resetStreak(current: UserStreak) {
        val currentTime = System.currentTimeMillis()
        val elapsed = currentTime - current.resetTimestamp

        val updated = current.copy(
            resetTimestamp = currentTime,
            personalBest = maxOf(current.personalBest, elapsed),
            isActive = false
        )

        viewModelScope.launch {
            dao.updateStreak(updated)

            _activeStreak.value = null
            timerJob?.cancel()
            _timer.value = StreakDuration(0, 0, 0, 0)

            loadRecentStreaks()
        }
    }

    fun updateStreakDetails(id: Int, name: String, clause: String) {
        viewModelScope.launch {
            val current = dao.getStreakById(id)
            if (current != null) {
                val updated = current.copy(name = name, resetClause = clause)
                dao.updateStreak(updated)
                _activeStreak.value = updated
                loadRecentStreaks()
            }
        }
    }

    fun setActiveStreak(streak: UserStreak) {
        viewModelScope.launch {
            dao.clearActiveStreak()
            val updated = streak.copy(
                resetTimestamp = System.currentTimeMillis(),
                isActive = true
            )
            dao.updateStreak(updated)
            loadRecentStreaks()
        }
    }

    private fun saveWidgetStreakToPrefs(resetTimestamp: Long) {
        val context = getApplication<Application>().applicationContext
        val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        prefs.edit { putLong("resetTimestamp", resetTimestamp) }

        val manager = GlanceAppWidgetManager(context)
        viewModelScope.launch {
            val ids = manager.getGlanceIds(StreakWidget::class.java)
            ids.forEach { id ->
                StreakWidget().update(context, id)
            }
        }
    }

    fun claimAchievement(streak: UserStreak, message: String?) {
        val claimed = ClaimedAchievement(
            name = streak.name,
            resetClause = streak.resetClause,
            datetimeClaimed = System.currentTimeMillis(),
            streakLengthDays = (System.currentTimeMillis() - streak.resetTimestamp) / (1000 * 60 * 60 * 24),
            userMessage = message
        )

        viewModelScope.launch {
            achievementDao.insertAchievement(claimed)
        }
    }

    fun getAchievements(): Flow<List<ClaimedAchievement>> {
        return achievementDao.getAllAchievements()
    }
}
