package com.nova.designsystem

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Reusable Design System Extensions for NOVA.
 * Includes Swipe-to-Reply Spring Container, Glass Modal Bottom Sheet, and Shimmer Cards.
 */
object NovaComponents {

  @Composable
  fun NovaContextSwipeContainer(
    onSwipeToReply: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
  ) {
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }

    Box(
      modifier = modifier
        .fillMaxWidth()
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
        .draggable(
          orientation = Orientation.Horizontal,
          state = rememberDraggableState { delta ->
            coroutineScope.launch {
              val newX = (offsetX.value + delta).coerceIn(0f, 160f)
              offsetX.snapTo(newX)
              if (newX >= 120f) {
                onSwipeToReply()
              }
            }
          },
          onDragStopped = {
            coroutineScope.launch {
              offsetX.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                  dampingRatio = Spring.DampingRatioMediumBouncy,
                  stiffness = Spring.StiffnessLow
                )
              )
            }
          }
        )
    ) {
      content()
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun NovaGlassModalBottomSheet(
    onDismiss: () -> Unit,
    onActionSelected: (String) -> Unit
  ) {
    ModalBottomSheet(
      onDismissRequest = onDismiss,
      containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
      shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text(
          text = "Message Options",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier.padding(bottom = 12.dp)
        )

        BottomSheetActionRow(icon = Icons.Default.Reply, label = "Reply") { onActionSelected("reply"); onDismiss() }
        BottomSheetActionRow(icon = Icons.Default.FormatQuote, label = "Quote") { onActionSelected("quote"); onDismiss() }
        BottomSheetActionRow(icon = Icons.Default.Translate, label = "Translate with AI") { onActionSelected("translate"); onDismiss() }
        BottomSheetActionRow(icon = Icons.Default.AutoAwesome, label = "Summarize Message") { onActionSelected("summarize"); onDismiss() }
        BottomSheetActionRow(icon = Icons.Default.PushPin, label = "Pin Message") { onActionSelected("pin"); onDismiss() }
        BottomSheetActionRow(icon = Icons.Default.Delete, label = "Delete for Everyone", isDestructive = true) { onActionSelected("delete"); onDismiss() }
      }
    }
  }

  @Composable
  private fun BottomSheetActionRow(
    icon: ImageVector,
    label: String,
    isDestructive: Boolean = false,
    onClick: () -> Unit
  ) {
    val tint = if (isDestructive) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurface

    TextButton(
      onClick = onClick,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, color = tint, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
      }
    }
  }
}
