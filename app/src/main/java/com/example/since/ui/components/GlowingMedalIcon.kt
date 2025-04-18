package com.example.since.ui.components

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.since.R

@Composable
fun GlowingMedalIcon(
    habitName: String? = null,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val blurRadius = 70f
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val posterType = FontFamily(Font(R.font.outfit_regular))

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .graphicsLayer {
                    alpha = glowAlpha
                    renderEffect = RenderEffect
                        .createBlurEffect(
                            blurRadius,
                            blurRadius,
                            Shader.TileMode.CLAMP
                        )
                        .asComposeRenderEffect()
                }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color.Transparent),
                        center = Offset.Unspecified,
                        radius = 90f
                    ),
                    shape = CircleShape
                )
        )
Column (
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center

){
    Text( text = "$habitName",
        style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
        color = Color(0xFF002E69),
        fontFamily = posterType
        )
    Icon(
        imageVector = Icons.Default.MilitaryTech,
        contentDescription = "Achievement Medal",
        tint = Color(0xFF002E69),
        modifier = Modifier.size(80.dp)
    )
}
    }
}

@Preview
@Composable
fun PreviewGlowingMedalIcon() {
    GlowingMedalIcon(habitName = "No Distractions")
}