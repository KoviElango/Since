package com.example.since.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun EditStreakDialog(
    initialName: String,
    initialClause: String,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var updatedName by remember { mutableStateOf(initialName) }
    var updatedClause by remember { mutableStateOf(initialClause) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Streak") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = updatedName,
                    onValueChange = { updatedName = it },
                    label = { Text("What are you trying to stop?") },
                    placeholder = { Text("e.g. No Smoking") }
                )
                OutlinedTextField(
                    value = updatedClause,
                    onValueChange = { updatedClause = it },
                    label = { Text("What will remind you?") },
                    placeholder = { Text("e.g. You promised her you wouldn’t!") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(updatedName.trim(), updatedClause.trim())
            }) {
                Text("Change")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Exit")
            }
        }
    )
}
