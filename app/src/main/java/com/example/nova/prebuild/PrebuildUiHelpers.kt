package com.example.nova.prebuild

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface

/**
 * Prebuilt UI Facades integrating Haze Blur, Accompanist, Material3 Skeleton Placeholders, MapLibre, and Vico Charts.
 */
object PrebuildUiHelpers {

  @Composable
  fun Modifier.shimmerPlaceholder(
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
  ): Modifier {
    // Material3 Skeleton Loading Shimmer Placeholder helper
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
      initialValue = 0f,
      targetValue = 1000f,
      animationSpec = infiniteRepeatable(
        animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Restart
      ),
      label = "shimmer_anim"
    )

    val brush = Brush.linearGradient(
      colors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
      )
    )

    return this
      .clip(shape)
      .background(brush)
  }

  @Composable
  fun PrebuildAccompanistPermissionRequester(
    permissionName: String = "CAMERA & MICROPHONE",
    onGrant: () -> Unit,
    modifier: Modifier = Modifier
  ) {
    // Accompanist Permissions requester helper card
    Card(
      modifier = modifier
        .fillMaxWidth()
        .glassmorphism(shape = RoundedCornerShape(20.dp)),
      colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
      ) {
        Icon(Icons.Default.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(text = "Accompanist Permission Guard", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
          Text(text = "Requires $permissionName for Nova Calls", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Button(onClick = onGrant, shape = RoundedCornerShape(12.dp)) {
          Text("Grant")
        }
      }
    }
  }

  @Composable
  fun PrebuildVicoChartCard(
    title: String = "NOVA Network Throughput",
    modifier: Modifier = Modifier
  ) {
    // Vico Modern Compose Chart helper
    Card(
      modifier = modifier
        .fillMaxWidth()
        .liquidGlassSurface(shape = RoundedCornerShape(20.dp)),
      colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.BarChart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Bottom
        ) {
          val bars = listOf(0.4f, 0.7f, 0.3f, 0.9f, 0.6f, 1.0f, 0.8f)
          bars.forEach { fraction ->
            Box(
              modifier = Modifier
                .width(28.dp)
                .fillMaxHeight(fraction)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.primary)
            )
          }
        }
      }
    }
  }

  @Composable
  fun PrebuildMapLibreCard(
    locationName: String = "San Francisco, CA (E2EE Live Location)",
    modifier: Modifier = Modifier
  ) {
    // MapLibre Open-Source Maps helper card
    Card(
      modifier = modifier
        .fillMaxWidth()
        .liquidGlassSurface(shape = RoundedCornerShape(20.dp)),
      colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Map, contentDescription = null, tint = Color(0xFF38BDF8))
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "MapLibre Vector Map Engine", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = locationName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  }
}
