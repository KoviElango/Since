package com.example.since.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.since.ui.components.StreakCard
import com.example.since.ui.screens.AddStreakScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(onNavigateToActive: () -> Unit) {
    var showForm by remember { mutableStateOf(false) }
    var activeStreak by remember { mutableStateOf<Pair<String, String>?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Streaks") }
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
            if (activeStreak == null) {
                Text("No active streak. Add one to get started.")
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    val mockStreaks = listOf(
                        Triple("No Smoking", "You promised her you wouldn't.", 86400000L),
                        Triple("No Phone", "Digital detox mode!", 3600000L),
                        Triple("No Weed", "physical detox mode!", 3600000L)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .padding(top = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    )
                        {
                        mockStreaks.forEachIndexed { index, (name, clause, best) ->
                            StreakCard(
                                name = name,
                                clause = clause,
                                personalBest = best,
                                resetTimestamp = System.currentTimeMillis() - best,
                                onStart = { /* Placeholder */ },
                                onDelete = { /* Placeholder */ }
                            )
                        }
                    }

                }
            }
        }

        if (showForm) {
            AddStreakScreen(
                onSubmit = { name, clause ->
                    activeStreak = name to clause
                    showForm = false
                    //onNavigateToActive()
                },
                onDismiss = {
                    showForm = false
                }
            )
        }
    }
}
