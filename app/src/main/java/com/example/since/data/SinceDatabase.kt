package com.example.since.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserStreak::class], version = 1, exportSchema = false)
abstract class SinceDatabase : RoomDatabase() {
    abstract fun streakDao(): StreakDao

    companion object {
        @Volatile
        private var INSTANCE: SinceDatabase? = null

        fun getDatabase(context: Context): SinceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SinceDatabase::class.java,
                    "since_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
