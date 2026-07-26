package com.example.nova.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface
import com.example.nova.ui.components.NovaTopBar

data class NotificationItem(
  val id: String,
  val title: String,
  val body: String,
  val timestamp: String,
  val isE2ee: Boolean = true
)

@Composable
fun NotificationsScreen(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isQuietHoursOn by remember { mutableStateOf(false) }

  val notifications = listOf(
    NotificationItem("n1", "Sarah Connor 🔒", "Encrypted ratchet session keys updated", "10m ago"),
    NotificationItem("n2", "NOVA AI Studio 🤖", "Thread summary ready for core architecture chat", "1h ago"),
    NotificationItem("n3", "NOVA Developer Guild 🚀", "Marcus Vance pinned a new Compose benchmark note", "3h ago")
  )

  Column(modifier = modifier.fillMaxSize()) {
    NovaTopBar(
      title = "Notifications Feed",
      subtitle = "Signal E2EE Decrypted • Quiet Hours",
      isE2ee = true,
      onBackClick = onBackClick
    )

    // Quiet Hours Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .liquidGlassSurface(shape = RoundedCornerShape(20.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          if (isQuietHoursOn) Icons.Default.NotificationsOff else Icons.Default.Notifications,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(text = "Quiet Hours Mode", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
          Text(text = if (isQuietHoursOn) "Active — Non-urgent notifications muted" else "Inactive — All E2EE alerts enabled", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = isQuietHoursOn, onCheckedChange = { isQuietHoursOn = it })
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
      modifier = Modifier.fillMaxWidth().weight(1f),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(notifications) { item ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .glassmorphism(shape = RoundedCornerShape(18.dp)),
          colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = item.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
              Text(text = item.timestamp, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
          }
        }
      }
    }
  }
}
