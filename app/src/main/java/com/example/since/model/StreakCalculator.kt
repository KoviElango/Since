package com.example.since.model

import java.util.concurrent.TimeUnit


object StreakCalculator {

    fun getDetailedDurationSince(resetTimestamp: Long, currentTimestamp: Long = System.currentTimeMillis()): StreakDuration {
        val diffMillis = currentTimestamp - resetTimestamp

        val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffMillis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis) % 60

        return StreakDuration(days, hours, minutes, seconds)
    }

    fun getFullDaysSince(resetTimestamp: Long, currentTimestamp: Long = System.currentTimeMillis()): Long {
        return TimeUnit.MILLISECONDS.toDays(currentTimestamp - resetTimestamp)
    }
}