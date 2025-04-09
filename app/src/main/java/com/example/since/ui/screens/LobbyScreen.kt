package com.example.since.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.since.ui.screens.AddStreakScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(modifier: Modifier = Modifier) {
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
                    Text(text = "Streak: ${activeStreak!!.first}", style = MaterialTheme.typography.headlineSmall)
                    Text(text = "\"${activeStreak!!.second}\"", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        if (showForm) {
            AddStreakScreen(
                onSubmit = { name, clause ->
                    activeStreak = name to clause
                    showForm = false
                },
                onDismiss = {
                    showForm = false
                }
            )
        }
    }
}
