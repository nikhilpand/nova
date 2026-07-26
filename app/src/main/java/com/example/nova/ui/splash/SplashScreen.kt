package com.example.nova.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.theme.liquidGlassSurface
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onSplashFinished: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scale = remember { Animatable(0.4f) }

  LaunchedEffect(Unit) {
    scale.animateTo(
      targetValue = 1.0f,
      animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
      )
    )
    delay(1400)
    onSplashFinished()
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFF0A0014)),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.scale(scale.value)
    ) {
      Box(
        modifier = Modifier
          .size(110.dp)
          .liquidGlassSurface(shape = CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          Icons.Default.AutoAwesome,
          contentDescription = "NOVA",
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(56.dp)
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "NOVA",
        fontSize = 38.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        letterSpacing = 4.sp
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Signal E2EE • Material Expressive",
          style = MaterialTheme.typography.bodySmall,
          color = Color.White.copy(alpha = 0.7f)
        )
      }
    }
  }
}
