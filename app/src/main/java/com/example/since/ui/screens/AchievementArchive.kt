package com.example.since.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.since.R
import com.example.since.data.ClaimedAchievement
import com.example.since.ui.components.AchievementDetailDialog
import com.example.since.ui.components.GlowingMedalIcon
import com.example.since.viewmodel.MainViewModel


@Composable
fun AchievementArchiveScreen(viewModel: MainViewModel) {
    val achievements by viewModel.getAchievements().collectAsState(initial = emptyList())
    AchievementArchiveContent(achievements = achievements)
}

@Composable
fun AchievementArchiveContent(achievements: List<ClaimedAchievement>) {
    val posterType = FontFamily(Font(R.font.outfit_regular))
    val groupedRows = achievements.chunked(3)

    var selectedAchievement by remember { mutableStateOf<ClaimedAchievement?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(colors = listOf(Color(0xFF000000), Color(0xFF002E69)))
                )
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Achievement Archive",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(40.dp),
                fontFamily = posterType
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(50.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(groupedRows) { row ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        row.forEach { achievement ->
                            Box(modifier = Modifier.clickable { selectedAchievement = achievement }) {
                                GlowingMedalIcon(
                                    habitName = achievement.name,
                                    medalColor = Color(0xFFFF9900),
                                    medalSize = 120
                                )
                            }
                        }
                    }
                }
            }
            selectedAchievement?.let {
                AchievementDetailDialog(
                    achievement = it,
                    onDismiss = { selectedAchievement = null }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAchievementArchiveScreen() {
    val mockAchievements = listOf(
        ClaimedAchievement(name = "No Distractions", resetClause = "", datetimeClaimed = 0, streakLengthDays = 21, userMessage = "Felt great!"),
        ClaimedAchievement(name = "No Soda", resetClause = "", datetimeClaimed = 0, streakLengthDays = 45, userMessage = "Healthier choices now"),
        ClaimedAchievement(name = "Morning Run", resetClause = "", datetimeClaimed = 0, streakLengthDays = 60, userMessage = "Broke the laziness"),
        ClaimedAchievement(name = "No Instagram", resetClause = "", datetimeClaimed = 0, streakLengthDays = 30, userMessage = null),
        ClaimedAchievement(name = "Morning Run", resetClause = "", datetimeClaimed = 0, streakLengthDays = 60, userMessage = "Broke the laziness"),
        ClaimedAchievement(name = "No Instagram", resetClause = "", datetimeClaimed = 0, streakLengthDays = 30, userMessage = null)
    )
    AchievementArchiveContent(achievements = mockAchievements)
}

