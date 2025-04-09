package com.example.since.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.since.model.StreakDuration
import com.example.since.viewmodel.MainViewModel

@Composable
fun ActiveStreakScreen(viewModel: MainViewModel = viewModel()) {
    val timer by viewModel.timer.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Time Since Start",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = formatDuration(timer),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

private fun formatDuration(duration: StreakDuration): String {
    return "${duration.days}d ${duration.hours}h ${duration.minutes}m ${duration.seconds}s"
}
