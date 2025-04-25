package com.example.since.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an ongoing or past habit streak tracked by the user.
 *
 * Stored in the "streaks" table in the Room database.
 *
 * @property id Auto-generated primary key.
 * @property name Name of the habit or challenge (e.g. "Quit Smoking").
 * @property resetClause string entered by user and displayed when breaking the streak.
 * @property resetTimestamp Epoch timestamp when the streak was last reset.
 * @property personalBest Longest duration (in milliseconds) maintained for this streak.
 * @property isActive Boolean indicating if this streak is currently being tracked.
 */

@Entity(tableName = "streaks")
data class UserStreak(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val resetClause: String,
    val resetTimestamp: Long,
    val personalBest: Long = 0L,
    val isActive: Boolean = false
)



