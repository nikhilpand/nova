package com.example.nova

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object ChatListKey : NavKey

@Serializable
data class ChatDetailKey(val conversationId: String) : NavKey

@Serializable
object ProfileKey : NavKey

@Serializable
object CommunitiesKey : NavKey

@Serializable
object AiWorkspaceKey : NavKey

@Serializable
object SettingsKey : NavKey
