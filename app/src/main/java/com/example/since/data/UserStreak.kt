package com.example.since.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streaks")
data class UserStreak(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val resetClause: String,
    val resetTimestamp: Long,
    val personalBest: Long = 0L,
    val isActive: Boolean = false
)

