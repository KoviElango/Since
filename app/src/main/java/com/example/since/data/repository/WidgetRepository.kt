package com.example.since.data.repository

interface WidgetRepository {
    fun saveResetTimestamp(timestamp: Long)
    fun getResetTimestamp(): Long
}
