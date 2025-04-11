package com.example.since.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.since.data.SinceDatabase
import com.example.since.data.StreakDao
import com.example.since.data.UserStreak
import com.example.since.model.StreakCalculator
import com.example.since.model.StreakDuration
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

    private var timerJob: Job? = null

    init {
        loadRecentStreaks()
    }

    fun loadRecentStreaks() {
        viewModelScope.launch {
            val recent = dao.getRecentStreaks()
            _streaks.value = recent

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
                _timer.value = StreakCalculator.getDetailedDurationSince(resetTime)
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


}
