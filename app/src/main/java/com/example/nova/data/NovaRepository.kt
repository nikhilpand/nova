package com.example.nova.data

import com.example.nova.security.CryptoManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class NovaRepository(
  private val cryptoManager: CryptoManager = CryptoManager()
) {
  val currentUser = User(
    id = "user_me",
    username = "alex_nova",
    displayName = "Alex Rivers",
    bio = "Building the future of Android UI & E2EE messaging.",
    pronouns = "they/them",
    statusText = "⚡ Crafting Jetpack Compose motion physics",
    accentColorHex = "#7C4DFF"
  )

  private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
  val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

  private val _messages = MutableStateFlow<Map<String, List<Message>>>(emptyMap())
  val messages: StateFlow<Map<String, List<Message>>> = _messages.asStateFlow()

  private val _communities = MutableStateFlow<List<Community>>(emptyList())
  val communities: StateFlow<List<Community>> = _communities.asStateFlow()

  init {
    loadMockData()
  }

  private fun loadMockData() {
    val conv1 = Conversation(
      id = "conv_signal",
      title = "Sarah Connor 🔒",
      avatarUrl = "",
      lastMessage = "The Signal protocol keys have been verified. Launching E2EE channel.",
      lastMessageTime = "19:14",
      unreadCount = 2,
      isPinned = true,
      isSecret = true,
      category = ChatCategory.SECRET
    )

    val conv2 = Conversation(
      id = "conv_android_team",
      title = "NOVA Core Architects 🚀",
      avatarUrl = "",
      lastMessage = "Compose 1.8 Liquid Glass shaders look incredible on 120Hz displays!",
      lastMessageTime = "18:45",
      unreadCount = 5,
      isGroup = true,
      isPinned = true,
      category = ChatCategory.WORK
    )

    val conv3 = Conversation(
      id = "conv_ai_agent",
      title = "NOVA AI Assistant 🤖",
      avatarUrl = "",
      lastMessage = "I summarized the 45 unread community messages into 3 bullet points.",
      lastMessageTime = "17:30",
      unreadCount = 0,
      category = ChatCategory.PERSONAL
    )

    _conversations.value = listOf(conv1, conv2, conv3)

    // Preload message threads
    val msgsConv1 = listOf(
      Message(
        conversationId = "conv_signal",
        senderId = "sarah",
        senderName = "Sarah Connor",
        content = "Hey Alex! Is our private ratcheting session active?",
        type = MessageType.TEXT,
        timestamp = System.currentTimeMillis() - 600000
      ),
      Message(
        conversationId = "conv_signal",
        senderId = "user_me",
        senderName = "Alex Rivers",
        content = "Yes! AES-256-GCM hardware key protection is enabled.",
        type = MessageType.TEXT,
        timestamp = System.currentTimeMillis() - 300000
      ),
      Message(
        conversationId = "conv_signal",
        senderId = "sarah",
        senderName = "Sarah Connor",
        content = "```kotlin\n// Signal E2EE Key Exchange\nval safetyNumber = crypto.verifySafetyNumbers(myKey, peerKey)\nprintln(\"Safety Code: \$safetyNumber\")\n```",
        type = MessageType.CODE_SNIPPET,
        codeLanguage = "kotlin",
        timestamp = System.currentTimeMillis() - 100000
      )
    )

    val msgsConv2 = listOf(
      Message(
        conversationId = "conv_android_team",
        senderId = "dev_marcus",
        senderName = "Marcus Vance",
        content = "Which theme preset should be the default for NOVA Android release?",
        type = MessageType.POLL,
        pollOptions = listOf(
          PollOption(1, "AMOLED Dark (Zero Power)", 14, isVotedByMe = true),
          PollOption(2, "Liquid Glassmorphism", 28, isVotedByMe = false),
          PollOption(3, "Cyberpunk Neon", 9, isVotedByMe = false)
        ),
        timestamp = System.currentTimeMillis() - 1200000
      )
    )

    _messages.value = mapOf(
      "conv_signal" to msgsConv1,
      "conv_android_team" to msgsConv2
    )

    // Preload communities
    _communities.value = listOf(
      Community(
        id = "comm_nova",
        name = "NOVA Developer Guild",
        channels = listOf(
          CommunityChannel("c1", "announcements", "announcement"),
          CommunityChannel("c2", "compose-ui-lab", "text", unread = true),
          CommunityChannel("c3", "voice-lounge-1", "voice"),
          CommunityChannel("c4", "architecture-q-and-a", "forum")
        )
      ),
      Community(
        id = "comm_ai",
        name = "Android AI Pioneers",
        channels = listOf(
          CommunityChannel("c5", "on-device-models", "text"),
          CommunityChannel("c6", "smart-replies", "text")
        )
      )
    )
  }

  fun sendMessage(conversationId: String, text: String, type: MessageType = MessageType.TEXT): Message {
    val encrypted = cryptoManager.encryptMessage(text)
    val newMessage = Message(
      id = UUID.randomUUID().toString(),
      conversationId = conversationId,
      senderId = currentUser.id,
      senderName = currentUser.displayName,
      senderAvatar = currentUser.avatarUrl,
      content = text, // decrypted representation for display
      type = type,
      timestamp = System.currentTimeMillis(),
      status = MessageStatus.SENT,
      isE2ee = encrypted.isE2eeVerified
    )

    val currentList = _messages.value[conversationId] ?: emptyList()
    val updatedList = currentList + newMessage
    _messages.value = _messages.value.toMutableMap().apply { put(conversationId, updatedList) }

    // Update conversation preview
    _conversations.value = _conversations.value.map { conv ->
      if (conv.id == conversationId) {
        conv.copy(lastMessage = text, lastMessageTime = "Just now")
      } else conv
    }

    return newMessage
  }

  fun toggleVote(conversationId: String, messageId: String, optionId: Int) {
    val currentList = _messages.value[conversationId] ?: return
    val updatedList = currentList.map { msg ->
      if (msg.id == messageId && msg.pollOptions != null) {
        val newOptions = msg.pollOptions.map { opt ->
          if (opt.id == optionId) {
            val nextVoted = !opt.isVotedByMe
            opt.copy(
              isVotedByMe = nextVoted,
              votes = if (nextVoted) opt.votes + 1 else (opt.votes - 1).coerceAtLeast(0)
            )
          } else opt
        }
        msg.copy(pollOptions = newOptions)
      } else msg
    }
    _messages.value = _messages.value.toMutableMap().apply { put(conversationId, updatedList) }
  }
}
