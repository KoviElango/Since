package com.example.since.data.repository

import android.content.Context
import androidx.core.content.edit

class WidgetRepositoryImpl(private val context: Context) : WidgetRepository {

    private val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)

    override fun saveResetTimestamp(timestamp: Long) {
        prefs.edit { putLong("resetTimestamp", timestamp) }
    }

    override fun getResetTimestamp(): Long {
        val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        return prefs.getLong("resetTimestamp", 0L)
    }
}
