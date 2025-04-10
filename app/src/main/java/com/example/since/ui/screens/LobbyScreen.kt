package com.example.since.ui.screens

import android.util.Log
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(viewModel: MainViewModel, onNavigateToActive: () -> Unit) {
    val streaks by viewModel.streaks.collectAsState()
    var showForm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher),
                        contentDescription = "Since Logo",
                        modifier = Modifier
                            .height(120.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
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
                                onStart = {
                                    Log.d("LobbyScreen", "Start button clicked for ${streak.name}")
                                    viewModel.setActiveStreak(streak)
                                    onNavigateToActive()
                                },
                                onDelete = { viewModel.deleteStreak(streak) }
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
