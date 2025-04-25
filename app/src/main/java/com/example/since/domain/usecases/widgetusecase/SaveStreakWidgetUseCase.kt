package com.example.since.domain.usecases.widgetusecase

import android.content.Context
import androidx.core.content.edit
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.since.widget.StreakWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveStreakWidgetUseCase(
    private val context: Context
) {
    operator fun invoke(resetTimestamp: Long) {
        val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        prefs.edit { putLong("resetTimestamp", resetTimestamp) }

        val manager = GlanceAppWidgetManager(context)
        CoroutineScope(Dispatchers.IO).launch {
            val ids = manager.getGlanceIds(StreakWidget::class.java)
            ids.forEach { id ->
                StreakWidget().update(context, id)
            }
        }
    }
}