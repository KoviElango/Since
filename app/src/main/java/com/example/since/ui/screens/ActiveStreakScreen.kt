package com.example.since.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.since.ui.components.EditStreakDialog
import com.example.since.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveStreakScreen(
    viewModel: MainViewModel,
    onResetComplete: () -> Unit
) {
    val activeStreak by viewModel.activeStreak.collectAsState()
    val timer by viewModel.timer.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Since") }) },
        floatingActionButton = {
            activeStreak?.let {
                FloatingActionButton(onClick = { showEditDialog = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Streak")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            activeStreak?.let { streak ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 48.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(streak.name, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(32.dp))
                        Box(
                            modifier = Modifier
                                .padding(vertical = 32.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = "${timer.days}d ${timer.hours}h ${timer.minutes}m ${timer.seconds}s",
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                    Button(onClick = { showResetDialog = true }) {
                        Text("Reset")
                    }
                }
            } ?: Text(
                "No active streak. Start one from the lobby!",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    if (showResetDialog && activeStreak != null) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Are you sure?") },
            text = {
                Column {
                    Text("This will reset your current streak.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        activeStreak!!.resetClause,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showResetDialog = false
                    viewModel.resetStreak(activeStreak!!)
                    onResetComplete()
                }) {
                    Text("Yes, I give up")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("No, I can fight this")
                }
            }
        )
    }

    if (showEditDialog && activeStreak != null) {
        EditStreakDialog(
            initialName = activeStreak!!.name,
            initialClause = activeStreak!!.resetClause,
            onConfirm = { newName, newClause ->
                viewModel.updateStreakDetails(
                    id = activeStreak!!.id,
                    name = newName,
                    clause = newClause
                )
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
}
