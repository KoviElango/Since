package com.example.since.data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Represents a successfully completed streak that has been claimed as an achievement.
 *
 * Stored in the "achievements" table in the Room database.
 *
 * @property id Auto-generated primary key.
 * @property name Name of the streak (e.g. "No Soda Pop").
 * @property resetClause The reset condition/message shown when breaking the streak.
 * @property datetimeClaimed Epoch timestamp of when the achievement was claimed.
 * @property streakLengthDays Total duration of the streak in full days.
 * @property userMessage Optional reflection message the user entered at the time of claim.
 * @property type Type of achievement, defaulting to "habit".
 */

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
