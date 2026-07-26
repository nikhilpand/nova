package com.example.nova.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.data.ChatCategory
import com.example.nova.data.Conversation
import com.example.nova.theme.glassmorphism
import com.example.nova.ui.components.NovaAvatar
import com.example.nova.ui.components.NovaTopBar

@Composable
fun ChatListScreen(
  conversations: List<Conversation>,
  onConversationClick: (String) -> Unit,
  onAiClick: () -> Unit,
  onSettingsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCategory by remember { mutableStateOf(ChatCategory.ALL) }
  var searchQuery by remember { mutableStateOf("") }

  val filteredConversations = conversations.filter { conv ->
    val matchesCategory = (selectedCategory == ChatCategory.ALL) || (conv.category == selectedCategory)
    val matchesSearch = conv.title.contains(searchQuery, ignoreCase = true) || conv.lastMessage.contains(searchQuery, ignoreCase = true)
    matchesCategory && matchesSearch
  }

  Box(modifier = modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {
      NovaTopBar(
        title = "NOVA Messenger",
        subtitle = "End-to-End Encrypted • 120 FPS",
        isE2ee = true,
        onAiClick = onAiClick,
        onSettingsClick = onSettingsClick
      )

      // Search Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search encrypted chats, tags...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
          unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
          focusedBorderColor = MaterialTheme.colorScheme.primary,
          unfocusedBorderColor = Color.Transparent
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp)
      )

      // Folder Category Tabs
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(ChatCategory.values()) { category ->
          val isSelected = selectedCategory == category
          FilterChip(
            selected = isSelected,
            onClick = { selectedCategory = category },
            label = { Text(category.name.lowercase().replaceFirstChar { it.uppercase() }) },
            leadingIcon = if (category == ChatCategory.SECRET) {
              { Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(14.dp)) }
            } else null,
            shape = RoundedCornerShape(16.dp),
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primary,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
          )
        }
      }

      // Conversation List
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(filteredConversations) { conv ->
          ConversationRowItem(
            conversation = conv,
            onClick = { onConversationClick(conv.id) }
          )
        }
      }
    }

    // Floating Action Button
    FloatingActionButton(
      onClick = { /* New Chat */ },
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      shape = CircleShape,
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(bottom = 80.dp, end = 24.dp)
    ) {
      Icon(Icons.Default.Add, contentDescription = "New Chat")
    }
  }
}

@Composable
private fun ConversationRowItem(
  conversation: Conversation,
  onClick: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .glassmorphism(shape = RoundedCornerShape(18.dp), alpha = 0.12f, elevation = 2.dp)
      .clickable(onClick = onClick)
      .padding(14.dp)
  ) {
    NovaAvatar(name = conversation.title, isOnline = true, size = 52)

    Spacer(modifier = Modifier.width(14.dp))

    Column(modifier = Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = conversation.title,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.weight(1f)
        )
        if (conversation.isPinned) {
          Icon(
            Icons.Default.PinDrop,
            contentDescription = "Pinned",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
          text = conversation.lastMessageTime,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = conversation.lastMessage,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          modifier = Modifier.weight(1f)
        )
        if (conversation.unreadCount > 0) {
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.primary)
              .padding(horizontal = 8.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${conversation.unreadCount}",
              color = MaterialTheme.colorScheme.onPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
        }
      }
    }
  }
}
