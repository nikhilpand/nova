package com.example.nova.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.nova.ai.AiPlatformEngine
import com.example.nova.data.NovaRepository
import com.example.nova.theme.NovaThemePreset
import com.example.nova.ui.ai.AiAssistantScreen
import com.example.nova.ui.chat.ChatDetailScreen
import com.example.nova.ui.chat.ChatListScreen
import com.example.nova.ui.communities.CommunitiesScreen
import com.example.nova.ui.components.NovaBottomBar
import com.example.nova.ui.profile.ProfileScreen
import com.example.nova.ui.settings.SettingsScreen

@Composable
fun MainScreen(
  repository: NovaRepository = remember { NovaRepository() },
  aiEngine: AiPlatformEngine = remember { AiPlatformEngine() },
  currentPreset: NovaThemePreset = NovaThemePreset.DARK,
  onSelectPreset: (NovaThemePreset) -> Unit = {},
  modifier: Modifier = Modifier
) {
  var currentTab by remember { mutableStateOf("chats") }
  var selectedConversationId by remember { mutableStateOf<String?>(null) }

  val conversations by repository.conversations.collectAsState()
  val allMessages by repository.messages.collectAsState()
  val communities by repository.communities.collectAsState()

  Scaffold(
    bottomBar = {
      if (selectedConversationId == null) {
        NovaBottomBar(
          currentRoute = currentTab,
          onNavigate = { route ->
            currentTab = route
            selectedConversationId = null
          }
        )
      }
    },
    modifier = modifier.fillMaxSize()
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      if (selectedConversationId != null) {
        val targetId = selectedConversationId!!
        val conv = conversations.find { it.id == targetId } ?: conversations.first()
        val msgs = allMessages[targetId] ?: emptyList()

        ChatDetailScreen(
          conversation = conv,
          messages = msgs,
          onBackClick = { selectedConversationId = null },
          onSendMessage = { text, type ->
            repository.sendMessage(targetId, text, type)
          },
          onOptionVote = { msgId, optionId ->
            repository.toggleVote(targetId, msgId, optionId)
          },
          aiEngine = aiEngine
        )
      } else {
        when (currentTab) {
          "chats" -> ChatListScreen(
            conversations = conversations,
            onConversationClick = { id -> selectedConversationId = id },
            onAiClick = { currentTab = "ai" },
            onSettingsClick = { currentTab = "settings" }
          )

          "communities" -> CommunitiesScreen(
            communities = communities,
            onChannelClick = { currentTab = "chats" }
          )

          "ai" -> AiAssistantScreen(aiEngine = aiEngine)

          "profile" -> ProfileScreen(
            user = repository.currentUser,
            onSettingsClick = { currentTab = "settings" }
          )

          "settings" -> SettingsScreen(
            currentPreset = currentPreset,
            onSelectPreset = onSelectPreset
          )
        }
      }
    }
  }
}
