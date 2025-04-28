package com.example.since.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.*
import com.example.since.MainActivity
import com.example.since.data.repository.WidgetRepositoryImpl
import com.example.since.domain.usecases.widgetusecase.GetWidgetStateUseCase
import com.example.since.model.StreakWidgetState

class StreakWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val widgetRepository = WidgetRepositoryImpl(context)
        val getWidgetStateUseCase = GetWidgetStateUseCase(widgetRepository)
        val widgetState = getWidgetStateUseCase.invoke()

        provideContent {
            MyGlanceWidgetContent(widgetState)
        }
    }
}

@Composable
fun MyGlanceWidgetContent(state: StreakWidgetState) {
    GlanceTheme {
        val colors = GlanceTheme.colors

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(colors.primary)
                .clickable(onClick = actionStartActivity<MainActivity>())
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is StreakWidgetState.Active -> {
                    Column(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${state.days}",
                            style = TextStyle(
                                color = colors.onPrimary,
                                fontSize = 90.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        )
                        Text(
                            text = if (state.days == 1L) "day since..." else "days since...",
                            style = TextStyle(
                                color = colors.onPrimary,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
                StreakWidgetState.NoActiveStreak -> {
                    Text(
                        text = "No active streak\nTap to start!",
                        style = TextStyle(
                            color = colors.onPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                }
            }
        }
    }
}
