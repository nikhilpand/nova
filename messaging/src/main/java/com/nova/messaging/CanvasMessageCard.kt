package com.nova.messaging

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.theme.liquidGlassSurface

/**
 * Interactive Canvas & Whiteboard Message Card Component.
 */
@Composable
fun CanvasMessageCard(
  title: String = "NOVA Architecture Whiteboard Sketch",
  authorName: String = "Alex Rivers",
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .liquidGlassSurface(shape = RoundedCornerShape(20.dp)),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Brush, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "🎨 WHITEBOARD CANVAS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      }

      Spacer(modifier = Modifier.height(8.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(130.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(Color(0xFF0F172A))
          .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(Icons.Default.Gesture, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(40.dp))
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
          Text(text = "Drawn by $authorName • Tap to edit canvas", color = Color(0xFF38BDF8), fontSize = 11.sp)
        }
      }
    }
  }
}
