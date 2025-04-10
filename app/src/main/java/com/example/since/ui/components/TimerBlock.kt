package com.example.since.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun TimerBlock(value: Long, label: String, large: Boolean = false) {
    val valueStyle = if (large) {
        MaterialTheme.typography.displayLarge.copy(fontFamily = FontFamily.Monospace)
    } else {
        MaterialTheme.typography.headlineLarge.copy(fontFamily = FontFamily.Monospace)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .defaultMinSize(minWidth = if (large) 80.dp else 64.dp)
            .padding(2.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = 12.dp, horizontal = if (large) 20.dp else 16.dp)
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            style = valueStyle
        )
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
