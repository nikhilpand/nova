package com.example.nova.ai

import android.content.Context
import android.net.Uri
import com.example.nova.data.Message
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AiPlatformEngine {

    private val recognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    suspend fun extractOcrTextFromImage(context: Context, imageUri: Uri): String {
        return suspendCancellableCoroutine { continuation ->
            try {
                val image = InputImage.fromFilePath(context, imageUri)
                recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        continuation.resume(visionText.text.ifBlank { "No text detected in image." })
                    }
                    .addOnFailureListener { e ->
                        continuation.resume("OCR Error: ${e.localizedMessage}")
                    }
            } catch (e: Exception) {
                continuation.resume("Failed to process image: ${e.localizedMessage}")
            }
        }
    }

    fun generateSmartReplies(lastMessage: String): List<String> {
        if (lastMessage.isBlank()) return emptyList()
        val text = lastMessage.lowercase()
        return when {
            text.contains("verify") || text.contains("e2ee") -> listOf(
                "Verified! E2EE is active 🔒",
                "Ratchet session is secure.",
                "Share the safety code."
            )
            text.contains("meeting") || text.contains("call") || text.contains("time") -> listOf(
                "I'm available now.",
                "Let's jump on a WebRTC call.",
                "Send an invite link."
            )
            text.contains("code") || text.contains("pr") || text.contains("build") -> listOf(
                "I'll review the PR.",
                "Build is passing ✅",
                "Pushing the latest commit."
            )
            else -> listOf(
                "Sounds good!",
                "Thanks for the update.",
                "Let me look into this."
            )
        }
    }

    fun summarizeThread(messages: List<Message>): String {
        if (messages.isEmpty()) return "No messages in thread."
        val senderCount = messages.map { it.senderName }.distinct().size
        val totalWords = messages.sumOf { it.content.split("\\s+".toRegex()).size }
        val topics = messages.takeLast(5).joinToString(", ") { it.content.take(30) }
        return "💡 **Thread Summary:**\n• ${messages.size} messages exchanged between $senderCount contributors ($totalWords total words).\n• Recent context: $topics\n• Action Item: Review conversation thread."
    }

    fun rewriteText(original: String, tone: AiTone): String {
        return when (tone) {
            AiTone.PROFESSIONAL -> "Regarding our discussion: $original"
            AiTone.CONCISE -> "Key point: $original"
            AiTone.CREATIVE -> "✨ $original 🚀"
            AiTone.PIRATE -> "Ahoy! $original Arrr!"
        }
    }

    fun extractActionTasks(text: String): List<String> {
        val tasks = mutableListOf<String>()
        val lines = text.split("\n")
        for (line in lines) {
            if (line.contains("todo", ignoreCase = true) || line.contains("fix", ignoreCase = true) || line.contains("need to", ignoreCase = true)) {
                tasks.add("Task: ${line.trim()}")
            }
        }
        if (tasks.isEmpty()) {
            tasks.add("Task: Follow up on \"${text.take(40)}...\"")
        }
        return tasks
    }
}

enum class AiTone {
    PROFESSIONAL, CONCISE, CREATIVE, PIRATE
}
