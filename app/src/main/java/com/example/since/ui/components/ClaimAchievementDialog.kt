package com.example.since.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.since.R

@Composable
fun ClaimAchievementDialog(
    onDismiss: () -> Unit,
    onConfirm: (String?) -> Unit
) {
    var userMessage by remember { mutableStateOf("") }

    val posterStyle = FontFamily(Font(R.font.boldonse_regular))
    val posterType = FontFamily(Font(R.font.outfit_regular))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFD84D), Color(0xFFFF8C00))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You won this round!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF002E69),
                        fontFamily = posterType
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "This streak is proof of your discipline.",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF002E69),
                        fontFamily = posterStyle
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You’ve stayed committed for 45 days. Long enough to rewire your mind and break the habit.",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF002E69),
                        fontFamily = posterStyle
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Claiming will retire the streak and add a trophy to your achievements!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF002E69),
                        fontFamily = posterType
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }


            //Achievement icon
            GlowingMedalIcon()



            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Reflection: A few words for your future self. What would you want to remember from this moment?",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF002E69)),
                    fontFamily = posterType,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color(0xFF002E69),
                        focusedTextColor = Color(0xFF002E69),
                        unfocusedTextColor = Color(0xFF002E69),
                        cursorColor = Color(0xFF002E69)
                    )
                )
            }


            Text(
                text = "You can either end it now and celebrate, or keep going until you’re sure the habit is truly behind you. You can claim the streak any time after this once you feel confident.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF002E69),
                    fontFamily = posterType
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF002E69),
                        containerColor = Color.Transparent,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                    ),
                    border = BorderStroke(1.dp, Color(0xFF002E69))
                ) {
                    Text("Keep Going")
                }

                Button(
                    onClick = { onConfirm(userMessage.takeIf { it.isNotBlank() }) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002E69))
                ) {
                    Text("Claim Achievement", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewClaimAchievementDialog() {
    MaterialTheme {
        ClaimAchievementDialog(
            onDismiss = {},
            onConfirm = { /* Preview handler */ }
        )
    }
}
