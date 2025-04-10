package com.example.since.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.since.ui.components.EditStreakDialog
import com.example.since.ui.components.TimerBlock
import com.example.since.viewmodel.MainViewModel
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.since.R

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
        topBar = { TopAppBar(title = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = "Since Logo",
                modifier = Modifier
                    .height(120.dp)
            )
        }) },
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
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF185A72).copy(alpha = 0.2f),
                                            Color(0xFF020824).copy(alpha = 0.4f)
                                        )
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(horizontal = 24.dp, vertical = 12.dp)
                                .semantics { contentDescription = "Timer showing your current streak duration" }
                        )
                        {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                TimerBlock(
                                    value = timer.days,
                                    label = "Days",
                                    large = true
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    TimerBlock(value = timer.hours, label = "Hours")
                                    TimerBlock(value = timer.minutes, label = "Minutes")
                                    TimerBlock(value = timer.seconds, label = "Seconds")
                                }
                            }

                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        Text(
                            """
                                “ You’ll never change your life until you change something you do daily. 
                               The secret of your success is found in your daily routine. ”
                            """
                                .trimIndent(),
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.bodyMedium
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Message to yourself:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        activeStreak!!.resetClause,
                        style = MaterialTheme.typography.titleMedium
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
                Button(onClick = { showResetDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF4CAF50),
                        Color(0xFF000000)
                    )
                ) {
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
