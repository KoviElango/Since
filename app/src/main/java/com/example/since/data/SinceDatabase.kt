package com.example.since.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [UserStreak::class, ClaimedAchievement::class],
    version = 2,
    exportSchema = false
)
abstract class SinceDatabase : RoomDatabase() {
    abstract fun streakDao(): StreakDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: SinceDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `achievements` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `name` TEXT NOT NULL,
                        `resetClause` TEXT NOT NULL,
                        `datetimeClaimed` INTEGER NOT NULL,
                        `streakLengthDays` INTEGER NOT NULL,
                        `userMessage` TEXT,
                        `type` TEXT NOT NULL DEFAULT 'habit'
                    )
                """.trimIndent())
            }
        }

        fun getDatabase(context: Context): SinceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SinceDatabase::class.java,
                    "since_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
