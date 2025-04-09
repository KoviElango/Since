package com.example.since.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.since.model.StreakDuration
import java.text.SimpleDateFormat
import java.util.*

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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("“$clause”", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Personal Best: ${formatDuration(personalBest)}", style = MaterialTheme.typography.labelSmall)
            Text("Last reset: $formattedDate", style = MaterialTheme.typography.labelSmall)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { /* TODO: onStart callback */ }) {
                    Text("Start")
                }
                TextButton(onClick = onDelete) {
                    Text("Delete")
                }
            }

        }
    }
}

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
