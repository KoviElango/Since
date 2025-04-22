package com.example.since.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.since.ui.components.GlowingMedalIcon

//mockup

val sampleAchievements = listOf(
    "No Distractions", "No Soda Pop", "No Cakes", "Early Mornings",
    "Focus Time", "No Socials", "Daily Workout", "Reading Habit",
    "Healthy Eating", "Night Routine"
)

fun chunkAchievements(list: List<String>, size: Int): List<List<String>> {
    return list.chunked(size)
}

@Composable
fun AchievementArchive() {
    val medalRows = remember { chunkAchievements(sampleAchievements, 3) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Magenta
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Achievement Archive",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF002E69),
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(medalRows) { row ->
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(row) { title ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                GlowingMedalIcon(title)
                            }
                        }
                    }
                }
            }
        }
        }

}

@Preview
@Composable
fun PreviewAchievementArchive() {
    AchievementArchive()
}