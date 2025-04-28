package com.example.since.data.repository

import android.content.Context
import androidx.core.content.edit

class WidgetRepositoryImpl(context: Context) : WidgetRepository {

    private val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)

    override fun saveResetTimestamp(timestamp: Long) {
        prefs.edit { putLong("widget_resetTimestamp", timestamp) }
    }

    override fun getResetTimestamp(): Long {
        return prefs.getLong("widget_resetTimestamp", 0L)
    }

    override fun saveIsActive(isActive: Boolean) {
        prefs.edit { putBoolean("widget_isActive", isActive) }
    }

    override fun getIsActive(): Boolean {
        return prefs.getBoolean("widget_isActive", false)
    }
}
