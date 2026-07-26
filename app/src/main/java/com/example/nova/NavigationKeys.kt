package com.example.nova

import kotlinx.serialization.Serializable

@Serializable
object ChatListRoute

@Serializable
data class ChatDetailRoute(val conversationId: String)

@Serializable
object ProfileRoute

@Serializable
object CommunitiesRoute

@Serializable
object AiWorkspaceRoute

@Serializable
object SettingsRoute
