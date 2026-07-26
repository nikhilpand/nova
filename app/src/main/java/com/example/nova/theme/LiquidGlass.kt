package com.example.nova.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.glassmorphism(
  shape: Shape = RoundedCornerShape(20.dp),
  alpha: Float = 0.15f,
  borderAlpha: Float = 0.25f,
  elevation: Dp = 8.dp
): Modifier {
  val surfaceColor = MaterialTheme.colorScheme.surface
  val primaryColor = MaterialTheme.colorScheme.primary

  val glassGradient = Brush.verticalGradient(
    colors = listOf(
      surfaceColor.copy(alpha = alpha + 0.1f),
      surfaceColor.copy(alpha = alpha)
    )
  )

  val borderGradient = Brush.verticalGradient(
    colors = listOf(
      Color.White.copy(alpha = borderAlpha),
      primaryColor.copy(alpha = borderAlpha * 0.5f)
    )
  )

  return this
    .shadow(elevation = elevation, shape = shape, spotColor = primaryColor.copy(alpha = 0.3f))
    .clip(shape)
    .background(glassGradient)
    .border(width = 1.dp, brush = borderGradient, shape = shape)
}

@Composable
fun Modifier.liquidGlassSurface(
  shape: Shape = RoundedCornerShape(24.dp),
  glowColor: Color = MaterialTheme.colorScheme.primary
): Modifier {
  val glassGradient = Brush.linearGradient(
    colors = listOf(
      glowColor.copy(alpha = 0.2f),
      Color.White.copy(alpha = 0.05f),
      glowColor.copy(alpha = 0.1f)
    )
  )

  val borderBrush = Brush.sweepGradient(
    colors = listOf(
      glowColor.copy(alpha = 0.6f),
      Color.White.copy(alpha = 0.2f),
      glowColor.copy(alpha = 0.6f)
    )
  )

  return this
    .shadow(elevation = 12.dp, shape = shape, ambientColor = glowColor, spotColor = glowColor)
    .clip(shape)
    .background(glassGradient)
    .border(width = 1.5.dp, brush = borderBrush, shape = shape)
}
