package com.example.nova.data

import java.util.UUID

enum class MessageType {
  TEXT, MARKDOWN, CODE_SNIPPET, IMAGE, VOICE_NOTE, POLL, CHECKLIST, CANVAS, DISAPPEARING, SECRET
}

enum class MessageStatus {
  SENDING, SENT, DELIVERED, READ, ENCRYPTED
}

enum class ChatCategory {
  ALL, PERSONAL, WORK, COMMUNITIES, SECRET
}

data class User(
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
  val badges: List<String> = listOf("Verified", "Early Supporter", "E2EE Master"),
  val safetyNumber: String = "48291-59102-39201-94812"
)

data class PollOption(
  val id: Int,
  val text: String,
  val votes: Int = 0,
  val isVotedByMe: Boolean = false
)

data class Message(
  val id: String = UUID.randomUUID().toString(),
  val conversationId: String,
  val senderId: String,
  val senderName: String,
  val senderAvatar: String = "",
  val content: String,
  val type: MessageType = MessageType.TEXT,
  val timestamp: Long = System.currentTimeMillis(),
  val status: MessageStatus = MessageStatus.READ,
  val isE2ee: Boolean = true,
  val codeLanguage: String? = null,
  val pollOptions: List<PollOption>? = null,
  val reactions: Map<String, Int> = emptyMap(),
  val voiceDurationSec: Int = 0,
  val waveformAmplitudes: List<Float> = emptyList(),
  val myReaction: String? = null,
  val replyToId: String? = null,
  val replyToText: String? = null,
  val isPinned: Boolean = false,
  val isStarred: Boolean = false,
  val translation: String? = null,
  val aiSummary: String? = null
)

data class StatusStory(
  val id: String = UUID.randomUUID().toString(),
  val userName: String,
  val userAvatar: String = "",
  val timestamp: String,
  val isMine: Boolean = false,
  val hasUnseen: Boolean = true
)

data class Conversation(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val avatarUrl: String = "",
  val lastMessage: String,
  val lastMessageTime: String,
  val unreadCount: Int = 0,
  val isGroup: Boolean = false,
  val isPinned: Boolean = false,
  val isSecret: Boolean = false,
  val category: ChatCategory = ChatCategory.ALL,
  val participants: List<User> = emptyList(),
  val typingUser: String? = null
)

data class CommunityChannel(
  val id: String = UUID.randomUUID().toString(),
  val name: String,
  val type: String = "text", // text, voice, forum, announcement
  val unread: Boolean = false
)

data class Community(
  val id: String = UUID.randomUUID().toString(),
  val name: String,
  val iconUrl: String = "",
  val memberCount: Int = 1240,
  val channels: List<CommunityChannel> = emptyList()
)

