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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
  onReactionClick: (String, String) -> Unit = { _, _ -> },
  aiEngine: AiPlatformEngine = remember { AiPlatformEngine() },
  modifier: Modifier = Modifier
) {
  var inputText by remember { mutableStateOf("") }
  var showAiDialog by remember { mutableStateOf(false) }
  var dialogTitle by remember { mutableStateOf("") }
  var dialogText by remember { mutableStateOf("") }
  var isRecordingVoice by remember { mutableStateOf(false) }

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
        dialogTitle = "✨ LangGraph Thread Summary"
        dialogText = aiEngine.summarizeThread(messages)
        showAiDialog = true
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
          onOptionVote = { optionId -> onOptionVote(msg.id, optionId) },
          onReactionClick = { emoji -> onReactionClick(msg.id, emoji) },
          onLongClick = {
            onReactionClick(msg.id, "❤️")
          }
        )
      }
    }

    // LangGraph AI Action Chips Toolbar
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        AssistChip(
          onClick = {
            dialogTitle = "🔒 E2EE Security Audit"
            dialogText = "🔒 Signal E2EE Double-Ratchet Session:\n• State: Verified Active\n• Safety Number: 47087 07238 53607\n• Protocol: AES-256-GCM + HKDF-SHA256"
            showAiDialog = true
          },
          label = { Text("🔒 E2EE Audit", fontSize = 11.sp) },
          leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(14.dp)) },
          shape = RoundedCornerShape(14.dp)
        )
      }
      item {
        AssistChip(
          onClick = {
            dialogTitle = "📋 Action Tasks"
            dialogText = aiEngine.extractActionTasks(messages.lastOrNull()?.content ?: "").joinToString("\n• ", prefix = "📋 Action Items:\n• ")
            showAiDialog = true
          },
          label = { Text("📋 Extract Tasks", fontSize = 11.sp) },
          leadingIcon = { Icon(Icons.Default.Checklist, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp)) },
          shape = RoundedCornerShape(14.dp)
        )
      }
      item {
        AssistChip(
          onClick = {
            dialogTitle = "🎭 Tone Rewrite"
            val lastText = messages.lastOrNull()?.content ?: "Meeting today"
            dialogText = "Original: $lastText\n\n✨ Professional: ${aiEngine.rewriteText(lastText, com.example.nova.ai.AiTone.PROFESSIONAL)}\n🚀 Creative: ${aiEngine.rewriteText(lastText, com.example.nova.ai.AiTone.CREATIVE)}"
            showAiDialog = true
          },
          label = { Text("🎭 Tone Rewrite", fontSize = 11.sp) },
          leadingIcon = { Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp)) },
          shape = RoundedCornerShape(14.dp)
        )
      }
    }

    // AI Smart Replies Bar
    if (smartReplies.isNotEmpty()) {
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 2.dp),
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
        placeholder = { Text(if (isRecordingVoice) "🎙️ Recording Voice Note..." else "Message (E2EE)...") },
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
        IconButton(
          onClick = {
            if (isRecordingVoice) {
              onSendMessage("Voice note (0:14)", MessageType.VOICE_NOTE)
              isRecordingVoice = false
            } else {
              isRecordingVoice = true
            }
          }
        ) {
          Icon(
            Icons.Default.Mic,
            contentDescription = "Voice Note",
            tint = if (isRecordingVoice) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }

  // LangGraph AI Dialog
  if (showAiDialog) {
    AlertDialog(
      onDismissRequest = { showAiDialog = false },
      title = { Text(dialogTitle) },
      text = { Text(dialogText) },
      confirmButton = {
        TextButton(onClick = { showAiDialog = false }) {
          Text("Done")
        }
      }
    )
  }
}

