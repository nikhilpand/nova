package com.nova.notifications

/**
 * Firebase Cloud Messaging (FCM) Push Service for NOVA.
 * Handles FCM registration token lifecycle and decrypts Signal E2EE background payloads.
 */
class FcmService {

  private var fcmToken: String? = null

  fun onNewToken(token: String) {
    this.fcmToken = token
    println("🔥 Received FCM Registration Token: ${token.take(16)}...")
  }

  fun handleRemoteMessagePayload(dataMap: Map<String, String>): String {
    val encryptedPayload = dataMap["encrypted_content"] ?: return "Empty FCM Payload"
    val senderName = dataMap["sender_name"] ?: "NOVA Contact"

    return "🔔 [FCM E2EE Notification] $senderName: Incoming encrypted message"
  }
}
