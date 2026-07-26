package com.nova.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Firebase Cloud Messaging (FCM) Push Service for NOVA.
 * Extends FirebaseMessagingService to receive background push notifications
 * and process incoming Signal E2EE message payloads.
 */
class NovaFcmService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Store or transmit FCM token to backend / Supabase
        println("🔥 Received FCM Registration Token: ${token.take(16)}...")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val dataMap = message.data
        val encryptedPayload = dataMap["encrypted_content"]
        val senderName = dataMap["sender_name"] ?: "NOVA Contact"

        println("🔔 Notification from $senderName (Encrypted payload size: ${encryptedPayload?.length ?: 0})")
    }
}
