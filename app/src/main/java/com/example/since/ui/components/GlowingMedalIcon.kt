package com.example.since.ui.components

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.since.R

@Composable
fun GlowingMedalIcon(
    habitName: String? = null,
    medalColor: Color = Color(0xFF002E69),
    medalSize: Int = 120
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
        modifier = Modifier.size(medalSize.dp)
    ) {
        Box(
            modifier = Modifier
                .size(medalSize.dp * 0.75f)
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
    Icon(
        imageVector = Icons.Default.MilitaryTech,
        contentDescription = "Achievement Medal",
        tint = medalColor,
        modifier = Modifier.size(medalSize.dp * 0.75f)
    )
    Spacer(modifier = Modifier.height(8.dp))

    Box(
        Modifier
            .size(medalSize.dp * 0.75f)
            .clip(RoundedCornerShape(7.dp))
            .background(Color.White.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ){
        Text( text = "$habitName",
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
            letterSpacing = (-0.5).sp,
            color = Color.Black,
            fontFamily = posterType,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            modifier = Modifier.padding(0.5.dp)
        )
    }

}
    }
}

@Preview
@Composable
fun PreviewGlowingMedalIcon() {
    GlowingMedalIcon(habitName = "No Distractions")
}