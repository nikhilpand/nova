package com.nova.domain.usecases

import com.nova.domain.models.DomainMessage
import com.nova.domain.models.DomainMessageType
import java.util.UUID

class SendMessageUseCase {
  operator fun invoke(
    conversationId: String,
    senderId: String,
    senderName: String,
    text: String,
    type: DomainMessageType = DomainMessageType.TEXT
  ): DomainMessage {
    return DomainMessage(
      id = UUID.randomUUID().toString(),
      conversationId = conversationId,
      senderId = senderId,
      senderName = senderName,
      content = text,
      type = type,
      timestamp = System.currentTimeMillis(),
      isE2ee = true
    )
  }
}

class VerifyE2eeSafetyNumberUseCase {
  operator fun invoke(myKey: String, peerKey: String): String {
    val combined = (myKey + peerKey).hashCode()
    return String.format("%05d-%05d-%05d-%05d", (combined % 90000) + 10000, ((combined / 10) % 90000) + 10000, ((combined / 100) % 90000) + 10000, ((combined / 1000) % 90000) + 10000)
  }
}

class GenerateSmartReplyUseCase {
  operator fun invoke(lastMessage: String): List<String> {
    return when {
      lastMessage.contains("verified", ignoreCase = true) -> listOf("Awesome! E2EE secured 🔒", "Safety code matches.", "Proceed with chat.")
      lastMessage.contains("call", ignoreCase = true) -> listOf("Connecting WebRTC...", "Audio is clear!", "Sharing screen now.")
      else -> listOf("Sounds great!", "Send details.", "Understood.")
    }
  }
}

class ExtractActionTasksUseCase {
  operator fun invoke(text: String): List<String> {
    val tasks = mutableListOf<String>()
    if (text.contains("verify", ignoreCase = true) || text.contains("test", ignoreCase = true)) {
      tasks.add("Action: Verify Signal Double-Ratchet keys in KeyStore")
    }
    if (text.contains("code", ignoreCase = true) || text.contains("build", ignoreCase = true)) {
      tasks.add("Action: Audit 120 FPS Compose recomposition frame rate")
    }
    if (tasks.isEmpty()) {
      tasks.add("Action: Note - \"$text\"")
    }
    return tasks
  }
}
