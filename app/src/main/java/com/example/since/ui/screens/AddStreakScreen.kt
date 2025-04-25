package com.example.since.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


/**
 * AddStreakScreen allows users to input a new streak name and its reset clause.
 *
 * Typically shown as a dialog or overlay, it's invoked when the user wants to begin tracking a new habit.
 *
 * @param onSubmit Callback triggered with the new streak name and reset clause.
 * @param onDismiss Callback to dismiss the screen without submitting.
 */

@Composable
fun AddStreakScreen(
    onSubmit: (streakName: String, resetClause: String) -> Unit,
    onDismiss: () -> Unit
) {
    var streakName by remember { mutableStateOf("") }
    var resetClause by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "How to break a habit:",
                    style = MaterialTheme.typography.titleMedium)
                Text(
                    text = """
                        
                    •  Notice what triggers the habit  
                    •  Interrupt the pattern  
                    •  Replace it with something healthier  
                    •  Use this streak to stay committed
                    """.trimIndent(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "If you ever decide to break your streak, reset the timer first. Make it a choice, not a slip.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = streakName,
                    onValueChange = { streakName = it },
                    label = { Text("What am I trying to stop?") },
                    placeholder = { Text("No smoking") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = resetClause,
                    onValueChange = { resetClause = it },
                    label = { Text("What would remind me not to?") },
                    placeholder = { Text("You promised her you wouldn't!") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onSubmit(streakName.trim(), resetClause.trim())
                        },
                        enabled = streakName.isNotBlank() && resetClause.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
