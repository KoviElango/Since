package com.example.since.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.since.data.ClaimedAchievement
import com.example.since.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun AchievementDetailDialog(
    achievement: ClaimedAchievement,
    onDismiss: () -> Unit
) {
    val posterStyle = FontFamily(Font(R.font.stixtwotext_medium))
    val posterType = FontFamily(Font(R.font.outfit_regular))
    val secondaryColor = Color(0xFF002E69)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFFFF9900),
        title = {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ){
                GlowingMedalIcon(
                    habitName = achievement.name
                )
            }

        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = secondaryColor,
                                fontFamily = posterStyle,
                                letterSpacing = (-1).sp
                            )
                        ) {
                            append("Days streaked:  ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontFamily = posterStyle,
                                fontStyle = FontStyle.Italic,
                            )
                        ) {
                            append("${achievement.streakLengthDays}")
                        }
                    },
                    style = MaterialTheme.typography.titleLarge
                )

                achievement.userMessage?.let {
                    Text(
                        text = "Reflection:\n${achievement.userMessage}",
                        fontFamily = posterType,
                        color = secondaryColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Close",
                    fontFamily = posterType,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAchievementDetailDialog(
    achievement: ClaimedAchievement = ClaimedAchievement(
        name = "No Distractions",
        resetClause = "Take a break if mind wanders",
        datetimeClaimed = System.currentTimeMillis(),
        streakLengthDays = 21,
        userMessage = "It really helped me focus better.",
        type = "habit"
    )
) {
    AchievementDetailDialog(
        achievement = achievement,
        onDismiss = {}
    )
}
