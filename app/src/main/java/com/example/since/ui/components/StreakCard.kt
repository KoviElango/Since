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
    onDelete: () -> Unit,
    isActive: Boolean = false,
    onClick: () -> Unit,
    onResume: () -> Unit = {},
) {
    val formattedDate = rememberFormattedDate(resetTimestamp)

    val inactiveGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF040a22),
            Color(0xFF8097f2)
        )
    )
    val activeGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xff0d1401),
            Color(0xFF15a00c)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = if (isActive) activeGradient else inactiveGradient,
                    shape = RoundedCornerShape(16.dp)
                )
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
                    if (!isActive) {
                        OutlinedButton(onClick = onStart, shape = RoundedCornerShape(12.dp),) {
                            Text("Start")
                        }
                    } else {
                        OutlinedButton(
                            onClick = onResume,
                            colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF0d1401), containerColor = Color(0xFFCEF578)),
                            shape = RoundedCornerShape(12.dp))
                        {
                            Text("Active")
                        }
                    }

                    TextButton(onClick = onDelete) {
                        Text("Delete", color = Color.White)
                    }
                }
            }
        }
    }
}

