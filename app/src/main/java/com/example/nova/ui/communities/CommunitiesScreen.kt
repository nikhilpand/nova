package com.example.nova.ui.communities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Grid3x3
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.data.Community
import com.example.nova.data.CommunityChannel
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface
import com.example.nova.ui.components.NovaTopBar

@Composable
fun CommunitiesScreen(
  communities: List<Community>,
  onChannelClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCommunityId by remember { mutableStateOf(communities.firstOrNull()?.id ?: "") }
  val activeCommunity = communities.find { it.id == selectedCommunityId } ?: communities.firstOrNull()

  Column(modifier = modifier.fillMaxSize()) {
    NovaTopBar(
      title = "Communities & Guilds",
      subtitle = "Discord-style Channels & Voice Rooms",
      isE2ee = false
    )

    // Communities Row
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(communities) { comm ->
        val isSelected = comm.id == selectedCommunityId
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.clickable { selectedCommunityId = comm.id }
        ) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(RoundedCornerShape(if (isSelected) 16.dp else 24.dp))
              .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
              .padding(4.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = comm.name.firstOrNull()?.toString() ?: "G",
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp,
              color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = comm.name.take(8),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    if (activeCommunity != null) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp)
          .liquidGlassSurface(shape = RoundedCornerShape(20.dp))
          .padding(16.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Column(modifier = Modifier.weight(1f)) {
            Text(text = activeCommunity.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = "👥 ${activeCommunity.memberCount} Members • 8 Roles", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "CHANNELS & ROOMS",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 4.dp)
      )

      LazyColumn(
        modifier = Modifier.fillMaxWidth().weight(1f),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        items(activeCommunity.channels) { channel ->
          ChannelRowItem(channel = channel, onClick = { onChannelClick(channel.id) })
        }
      }
    }
  }
}

@Composable
private fun ChannelRowItem(channel: CommunityChannel, onClick: () -> Unit) {
  val icon = when (channel.type) {
    "voice" -> Icons.Default.Mic
    "announcement" -> Icons.Default.Campaign
    "forum" -> Icons.Default.Forum
    else -> Icons.Default.Tag
  }

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .glassmorphism(shape = RoundedCornerShape(14.dp), alpha = 0.1f, elevation = 1.dp)
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
    Spacer(modifier = Modifier.width(12.dp))
    Text(
      text = channel.name,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = if (channel.unread) FontWeight.Bold else FontWeight.Normal,
      color = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.weight(1f)
    )
    if (channel.unread) {
      Box(
        modifier = Modifier
          .size(8.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.primary)
      )
    }
  }
}
