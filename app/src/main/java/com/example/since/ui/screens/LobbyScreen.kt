package com.example.since.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.since.R
import com.example.since.data.UserStreak
import com.example.since.ui.components.StreakCard
import com.example.since.viewmodel.MainViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(viewModel: MainViewModel, onNavigateToActive: () -> Unit) {
    val streaks by viewModel.streaks.collectAsState()
    var showForm by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activeStreak by viewModel.activeStreak.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box (
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher),
                            contentDescription = "Since Logo",
                            modifier = Modifier
                                .height(120.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (activeStreak != null) {
                        Toast.makeText(
                            context,
                            "You’re already tracking a streak. Reset it before starting a new one.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else if (streaks.size < 3) {
                        showForm = true
                    } else {
                        Toast.makeText(
                            context,
                            "Oops! You’re already tracking 3 streaks. Try deleting one to start a new streak.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Streak")
            }

        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (streaks.isEmpty()) {
                Text("No active streak. Add one to get started.")
            }
            else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .padding(top = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        streaks.forEach { streak ->
                            StreakCard(
                                name = streak.name,
                                clause = streak.resetClause,
                                personalBest = streak.personalBest,
                                resetTimestamp = streak.resetTimestamp,
                                isActive = (streak.id == activeStreak?.id),
                                onStart = {
                                    if (activeStreak != null) {
                                        Toast.makeText(
                                            context,
                                            "You’re already tracking a streak. Reset it before starting a new one.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        viewModel.setActiveStreak(streak)
                                        onNavigateToActive()
                                    }
                                },
                                onDelete = {
                                    if (activeStreak?.id == streak.id) {
                                        Toast.makeText(
                                            context,
                                            "You can't delete an active streak. Please reset it first.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                    viewModel.deleteStreak(streak)}
                                           },
                                onClick = {
                                    onNavigateToActive()
                                },
                                onResume = {
                                    if (streak.isActive) {
                                        onNavigateToActive()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        if (showForm) {
            AddStreakScreen(
                onSubmit = { name, clause ->
                    val newStreak = UserStreak(
                        name = name,
                        resetClause = clause,
                        resetTimestamp = System.currentTimeMillis(),
                        personalBest = 0L
                    )
                    viewModel.addOrReplaceStreak(newStreak)
                    showForm = false
                    onNavigateToActive()
                },
                onDismiss = {
                    showForm = false
                }
            )
        }
    }
}
