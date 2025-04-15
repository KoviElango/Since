package com.example.since.widget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.FontFamily
import androidx.glance.background
import com.example.since.MainActivity

class StreakWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(Color(0xFFFF5722))
                    .clickable(onClick = actionStartActivity<MainActivity>())
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "18",
                        style = TextStyle(
                            color = ColorProvider(
                                day = Color.White,
                                night = Color.White
                            ),
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    )

                    Spacer(modifier = GlanceModifier.width(8.dp))

                    Text(
                        text = "days since...",
                        style = TextStyle(
                            color = ColorProvider(
                                day = Color.White,
                                night = Color.White
                            ),
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}
