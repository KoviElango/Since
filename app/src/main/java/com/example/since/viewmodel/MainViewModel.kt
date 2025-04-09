package com.example.since.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.since.data.SinceDatabase
import com.example.since.data.StreakDao
import com.example.since.data.UserStreak
import com.example.since.model.StreakCalculator
import com.example.since.model.StreakDuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: StreakDao = SinceDatabase.getDatabase(application).streakDao()

    private val _streaks = MutableStateFlow<List<UserStreak>>(emptyList())
    val streaks: StateFlow<List<UserStreak>> = _streaks.asStateFlow()

    private val _timer = MutableStateFlow(StreakDuration(0, 0, 0, 0))
    val timer: StateFlow<StreakDuration> = _timer.asStateFlow()

    init {
        loadRecentStreaks()
    }

    fun loadRecentStreaks() {
        viewModelScope.launch {
            val recent = dao.getRecentStreaks()
            _streaks.value = recent

            val active = recent.firstOrNull()
            active?.let {
                startTickingTimer(it.resetTimestamp)
            }
        }
    }

    fun startTickingTimer(resetTime: Long) {
        viewModelScope.launch {
            while (true) {
                _timer.value = StreakCalculator.getDetailedDurationSince(resetTime)
                delay(1000)
            }
        }
    }

    fun addOrReplaceStreak(newStreak: UserStreak) {
        viewModelScope.launch {
            val existing = dao.getRecentStreaks()

            if (existing.size < 3) {
                dao.insertStreak(newStreak)
            } else {
                val oldest = existing.minByOrNull { it.resetTimestamp }
                val updated = newStreak.copy(id = oldest!!.id)
                dao.updateStreak(updated)
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
            personalBest = maxOf(current.personalBest, elapsed)
        )

        viewModelScope.launch {
            dao.updateStreak(updated)
            loadRecentStreaks()
        }
    }
}
