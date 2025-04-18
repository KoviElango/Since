package com.example.since.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.since.R

@Composable
fun ClaimAchievementDialog(
    onDismiss: () -> Unit,
    onConfirm: (String?) -> Unit
) {
    var userMessage by remember { mutableStateOf("") }

    val posterStyle = FontFamily(Font(R.font.stixtwotext_medium))
    val posterType = FontFamily(Font(R.font.outfit_regular))
    val secondaryColor = Color(0xFF002E69)
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF9900), Color(0xFFFFB700))
                )
            )
            .padding(16.dp)
            .clickable { keyboardController?.hide()}
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

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = secondaryColor,
                                fontFamily = posterStyle,
                                letterSpacing = (-3).sp,
                            )
                        ) {
                            append("This streak is proof of your")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontFamily = posterStyle,
                                fontStyle = FontStyle.Italic,
                                letterSpacing = (-3).sp,
                            )
                        ) {
                            append("discipline")
                        }
                    },
                    style = MaterialTheme.typography.displayLarge,
                    lineHeight = MaterialTheme.typography.displayLarge.fontSize * 1,
                    textAlign = TextAlign.Left,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = secondaryColor,
                                fontFamily = posterStyle,
                                letterSpacing = (-1.4).sp
                            )
                        ) {
                            append("You’ve stayed committed for ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontFamily = posterStyle,
                                fontStyle = FontStyle.Italic,
                                letterSpacing = (-1.2).sp
                            )
                        ) {
                            append("45 days!")
                        }
                    },
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "LONG ENOUGH TO REWIRE YOUR MIND AND BREAK THE HABIT.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = secondaryColor,
                        fontFamily = posterType
                    ),
                    letterSpacing = (-0.5).sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


            }

            //Achievement icon
            GlowingMedalIcon()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
            ) {

                OutlinedTextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    label = {
                        Text(
                            text = "Reflection:",
                            style = MaterialTheme.typography.bodySmall.copy(color = secondaryColor),
                            fontFamily = posterType
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color(0xFF002E69),
                        focusedTextColor = Color(0xFF002E69),
                        unfocusedTextColor = Color(0xFF002E69),
                        cursorColor = Color(0xFF002E69)
                    ),
                    placeholder = {
                        Text(
                            text = "A few words for your future self. What would you want to remember from this moment?",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0x9530415E)),
                            fontFamily = posterType,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    }

                )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "CLAIM TO END YOUR STREAK AND EARN A TROPHY OR KEEP GOING UNTIL YOU’RE SURE THE HABIT IS TRULY BEHIND YOU, YOUR TROPHY WILL BE WAITING.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = secondaryColor,
                    fontFamily = posterType,
                    letterSpacing = (-0.5).sp,
                    fontStyle = FontStyle.Italic,
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
                Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = secondaryColor,
                        containerColor = Color.Transparent,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                    ),
                    border = BorderStroke(1.dp, secondaryColor)
                ) {
                    Text("Keep Going")
                }

                Button(
                    onClick = { onConfirm(userMessage.takeIf { it.isNotBlank() }) },
                    colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                ) {
                    Text("Claim Achievement", color = Color.White)
                }

            }
                Spacer(modifier = Modifier.height(16.dp))
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
