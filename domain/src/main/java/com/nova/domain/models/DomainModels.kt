package com.nova.domain.models

import java.util.UUID

enum class DomainMessageType {
  TEXT, MARKDOWN, CODE_SNIPPET, IMAGE, VOICE_NOTE, POLL, CHECKLIST, DISAPPEARING, SECRET
}

enum class DomainMessageStatus {
  SENDING, SENT, DELIVERED, READ, ENCRYPTED
}

data class DomainUser(
  val id: String = UUID.randomUUID().toString(),
  val username: String,
  val displayName: String,
  val avatarUrl: String = "",
  val bannerUrl: String = "",
  val bio: String = "",
  val pronouns: String = "",
  val statusText: String = "Online",
  val isOnline: Boolean = true,
  val accentColorHex: String = "#6366F1",
  val badges: List<String> = listOf("Verified", "E2EE Master"),
  val safetyNumber: String = "48291-59102-39201-94812"
)

data class DomainPollOption(
  val id: Int,
  val text: String,
  val votes: Int = 0,
  val isVotedByMe: Boolean = false
)

data class DomainMessage(
  val id: String = UUID.randomUUID().toString(),
  val conversationId: String,
  val senderId: String,
  val senderName: String,
  val senderAvatar: String = "",
  val content: String,
  val type: DomainMessageType = DomainMessageType.TEXT,
  val timestamp: Long = System.currentTimeMillis(),
  val status: DomainMessageStatus = DomainMessageStatus.READ,
  val isE2ee: Boolean = true,
  val codeLanguage: String? = null,
  val pollOptions: List<DomainPollOption>? = null,
  val isPinned: Boolean = false,
  val translation: String? = null,
  val aiSummary: String? = null
)

data class DomainConversation(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val avatarUrl: String = "",
  val lastMessage: String,
  val lastMessageTime: String,
  val unreadCount: Int = 0,
  val isGroup: Boolean = false,
  val isPinned: Boolean = false,
  val isSecret: Boolean = false
)

data class DomainCommunityChannel(
  val id: String = UUID.randomUUID().toString(),
  val name: String,
  val type: String = "text",
  val unread: Boolean = false
)

data class DomainCommunity(
  val id: String = UUID.randomUUID().toString(),
  val name: String,
  val avatarUrl: String = "",
  val memberCount: Int = 1,
  val channels: List<DomainCommunityChannel> = emptyList()
)
