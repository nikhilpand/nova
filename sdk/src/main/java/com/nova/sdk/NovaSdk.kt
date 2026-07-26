package com.nova.sdk

/**
 * NOVA Public SDK for third-party extension developers.
 * Provides stable APIs for plugins, bots, mini-apps, and theme packs.
 */
object NovaSdk {

  const val SDK_VERSION = "0.1.0-alpha"

  interface MessageHandler {
    fun onMessageReceived(chatId: String, senderId: String, text: String)
    fun onMessageSent(chatId: String, text: String)
  }

  interface ThemeProvider {
    val themeName: String
    val primaryColorHex: String
    val surfaceColorHex: String
    val isDarkMode: Boolean
  }

  interface BotCommandHandler {
    val commandPrefix: String
    fun onCommand(command: String, args: List<String>): String
  }

  fun getSdkInfo(): String {
    return """
      NOVA SDK v$SDK_VERSION
      APIs: MessageHandler, ThemeProvider, BotCommandHandler
      Extension Points: plugins/, bot-api/, miniapps/, theme-api/
    """.trimIndent()
  }
}
