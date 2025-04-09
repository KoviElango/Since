package com.example.since.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.since.model.StreakCalculator
import com.example.since.model.StreakDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _streak = MutableStateFlow(StreakDuration(0, 0, 0, 0))
    val streak: StateFlow<StreakDuration> = _streak.asStateFlow()

    private var resetTimestamp = System.currentTimeMillis()

    fun updateStreak() {
        _streak.value = StreakCalculator.getDetailedDurationSince(resetTimestamp)
    }

    fun reset() {
        resetTimestamp = System.currentTimeMillis()
        updateStreak()
    }

    init {
        updateStreak()
    }
}
