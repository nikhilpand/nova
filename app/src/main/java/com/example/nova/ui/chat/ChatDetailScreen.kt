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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nova.ai.AiPlatformEngine
import com.example.nova.data.Conversation
import com.example.nova.data.Message
import com.example.nova.data.MessageType
import com.example.nova.theme.liquidGlassSurface
import com.example.nova.ui.components.NovaMessageBubble
import com.example.nova.ui.components.NovaTopBar

@Composable
fun ChatDetailScreen(
  conversation: Conversation,
  messages: List<Message>,
  onBackClick: () -> Unit,
  onSendMessage: (String, MessageType) -> Unit,
  onOptionVote: (String, Int) -> Unit,
  aiEngine: AiPlatformEngine = remember { AiPlatformEngine() },
  modifier: Modifier = Modifier
) {
  var inputText by remember { mutableStateOf("") }
  var showAiSummaryDialog by remember { mutableStateOf(false) }
  var summaryText by remember { mutableStateOf("") }

  val smartReplies = remember(messages) {
    val lastText = messages.lastOrNull()?.content ?: ""
    aiEngine.generateSmartReplies(lastText)
  }

  Column(modifier = modifier.fillMaxSize()) {
    // Header TopBar
    NovaTopBar(
      title = conversation.title,
      subtitle = if (conversation.isSecret) "Signal Double-Ratchet E2EE" else "Online",
      isE2ee = true,
      onBackClick = onBackClick,
      onAiClick = {
        summaryText = aiEngine.summarizeThread(messages)
        showAiSummaryDialog = true
      }
    )

    // Message Thread
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
      reverseLayout = false
    ) {
      items(messages) { msg ->
        NovaMessageBubble(
          message = msg,
          isFromMe = msg.senderId == "user_me",
          onOptionVote = { optionId -> onOptionVote(msg.id, optionId) }
        )
      }
    }

    // AI Smart Replies Bar
    if (smartReplies.isNotEmpty()) {
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(smartReplies) { suggestion ->
          SuggestionChip(
            onClick = { onSendMessage(suggestion, MessageType.TEXT) },
            label = { Text("✨ $suggestion") },
            shape = RoundedCornerShape(16.dp),
            colors = SuggestionChipDefaults.suggestionChipColors(
              containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
              labelColor = MaterialTheme.colorScheme.primary
            )
          )
        }
      }
    }

    // Composer Input Bar
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .liquidGlassSurface(shape = RoundedCornerShape(28.dp))
        .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
      IconButton(onClick = { /* Attachment drawer */ }) {
        Icon(Icons.Default.AddCircle, contentDescription = "Attach", tint = MaterialTheme.colorScheme.primary)
      }

      OutlinedTextField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = { Text("Message (E2EE)...") },
        modifier = Modifier.weight(1f),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = Color.Transparent,
          unfocusedBorderColor = Color.Transparent
        )
      )

      if (inputText.isNotBlank()) {
        IconButton(
          onClick = {
            onSendMessage(inputText, MessageType.TEXT)
            inputText = ""
          }
        ) {
          Icon(
            Icons.Default.Send,
            contentDescription = "Send",
            tint = MaterialTheme.colorScheme.primary
          )
        }
      } else {
        IconButton(onClick = { /* Voice note record */ }) {
          Icon(Icons.Default.Mic, contentDescription = "Voice Note", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }
    }
  }

  // AI Summary Dialog
  if (showAiSummaryDialog) {
    AlertDialog(
      onDismissRequest = { showAiSummaryDialog = false },
      title = { Text("✨ NOVA AI Thread Summary") },
      text = { Text(summaryText) },
      confirmButton = {
        TextButton(onClick = { showAiSummaryDialog = false }) {
          Text("Done")
        }
      }
    )
  }
}
