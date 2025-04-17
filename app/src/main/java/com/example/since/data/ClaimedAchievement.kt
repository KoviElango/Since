package com.example.since.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class ClaimedAchievement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val resetClause: String,
    val datetimeClaimed: Long,
    val streakLengthDays: Long,
    val userMessage: String?,
    val type: String = "habit"
)
