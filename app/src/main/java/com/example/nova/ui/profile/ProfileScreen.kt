package com.example.nova.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.data.User
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface
import com.example.nova.ui.components.NovaAvatar
import com.example.nova.ui.components.NovaTopBar

@Composable
fun ProfileScreen(
  user: User,
  onSettingsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedAccent by remember { mutableStateOf(user.accentColorHex) }
  val accentColors = listOf("#7C4DFF", "#6366F1", "#10B981", "#FF007F", "#F59E0B", "#38BDF8")

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    NovaTopBar(
      title = "User Identity",
      subtitle = "@${user.username}",
      isE2ee = true,
      onSettingsClick = onSettingsClick
    )

    // Banner Header
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
        .padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(
          Brush.horizontalGradient(
            colors = listOf(
              Color(android.graphics.Color.parseColor(selectedAccent)),
              MaterialTheme.colorScheme.primaryContainer
            )
          )
        )
    )

    // Profile Card with Avatar Overlap
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .offset(y = (-40).dp)
    ) {
      NovaAvatar(name = user.displayName, isOnline = true, size = 84)

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = user.displayName,
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )

      Text(
        text = "@${user.username} • ${user.pronouns}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Badges Row
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        user.badges.forEach { badge ->
          AssistChip(
            onClick = {},
            label = { Text(badge, fontSize = 11.sp) },
            leadingIcon = { Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp)) },
            shape = RoundedCornerShape(12.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Bio Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .glassmorphism(shape = RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(text = "About Me", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = user.bio, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
          Spacer(modifier = Modifier.height(8.dp))
          Text(text = "Status: ${user.statusText}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // E2EE Safety Number Section
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .liquidGlassSurface(shape = RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Signal E2EE Safety Number", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(text = user.safetyNumber, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "Verified Hardware Key • AES-256-GCM Ratchet", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Accent Color Picker
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .glassmorphism(shape = RoundedCornerShape(20.dp))
          .padding(16.dp)
      ) {
        Text(text = "Identity Accent Color", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
          items(accentColors) { hex ->
            val color = Color(android.graphics.Color.parseColor(hex))
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color)
                .clickable { selectedAccent = hex },
              contentAlignment = Alignment.Center
            ) {
              if (selectedAccent == hex) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
              }
            }
          }
        }
      }
    }
  }
}
