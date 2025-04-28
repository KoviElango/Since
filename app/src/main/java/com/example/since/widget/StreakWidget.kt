package com.example.since.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.FontFamily
import androidx.glance.background
import com.example.since.MainActivity
import androidx.glance.GlanceTheme
import com.example.since.data.repository.WidgetRepositoryImpl

class StreakWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val widgetRepository = WidgetRepositoryImpl(context)
        provideContent {
            MyGlanceWidgetContent(widgetRepository)
        }
    }
}

private const val MILLIS_IN_A_DAY = 86_400_000L

@Composable
fun MyGlanceWidgetContent(widgetRepository: WidgetRepositoryImpl) {
    GlanceTheme {
        val colors = GlanceTheme.colors
        val resetTimestamp = widgetRepository.getResetTimestamp()
        val days = if (resetTimestamp > 0L) {
            (System.currentTimeMillis() - resetTimestamp) / MILLIS_IN_A_DAY
        } else null

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(colors.primary)
                .clickable(onClick = actionStartActivity<MainActivity>())
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (days != null) {
                    Text(
                        text = "$days",
                        style = TextStyle(
                            color = colors.onPrimary,
                            fontSize = 90.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                    )
                    Text(
                        text = if (days == 1L) "day since..." else "days since...",
                        style = TextStyle(
                            color = colors.onPrimary,
                            fontSize = 14.sp
                        )
                    )
                } else {
                    Text(
                        text = "No active streak",
                        style = TextStyle(
                            color = colors.onPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                }
            }
        }
    }
}
