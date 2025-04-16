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

class StreakWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyGlanceWidgetContent(context)
        }
    }
}

@Composable
fun MyGlanceWidgetContent(context: Context) {
    GlanceTheme {
        val colors = GlanceTheme.colors
        val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val resetTimestamp = prefs.getLong("resetTimestamp", 0L)
        val days = ((System.currentTimeMillis() - resetTimestamp) / (1000 * 60 * 60 * 24))

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
                    Text(
                        text = "$days",
                        style = TextStyle(
                            color = colors.onPrimary,
                            fontSize = 100.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                    )

                    Spacer(modifier = GlanceModifier.width(8.dp))

                    Text(
                        text = "days since...",
                        style = TextStyle(
                            color = colors.onPrimary,
                            fontSize = 14.sp
                        )
                    )
                }
            }

    }
}