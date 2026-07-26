package com.nova.botapi

/**
 * NOVA Bot & Automation Webhook API.
 * Allows third-party bots to listen for events and respond to commands.
 */
object BotApi {

  data class BotRegistration(
    val botId: String,
    val botName: String,
    val commandPrefix: String,
    val webhookUrl: String? = null
  )

  private val registeredBots = mutableListOf<BotRegistration>()

  fun registerBot(bot: BotRegistration) {
    registeredBots.add(bot)
    println("🤖 Registered bot: ${bot.botName} (${bot.commandPrefix})")
  }

  fun processCommand(input: String): String? {
    val bot = registeredBots.find { input.startsWith(it.commandPrefix) } ?: return null
    val args = input.removePrefix(bot.commandPrefix).trim()
    return "[${bot.botName}] Processing: $args"
  }
}
