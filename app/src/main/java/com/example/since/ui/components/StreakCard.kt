package com.example.since.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
private fun rememberFormattedDate(timestamp: Long): String {
    val formatter = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }
    return formatter.format(Date(timestamp))
}

private fun formatDuration(ms: Long): String {
    val days = ms / (1000 * 60 * 60 * 24)
    return "${days}d"
}

@Composable
fun StreakCard(
    name: String,
    clause: String,
    personalBest: Long,
    resetTimestamp: Long,
    onStart: () -> Unit,
    onDelete: () -> Unit
) {
    val formattedDate = rememberFormattedDate(resetTimestamp)

    val pastelGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF020824),
            Color(0xFF185A72)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(brush = pastelGradient, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "Personal Best: ${formatDuration(personalBest)}",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    "Last reset: $formattedDate",
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onStart, shape = RoundedCornerShape(12.dp)) {
                        Text("Start")
                    }
                    TextButton(onClick = onDelete) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

