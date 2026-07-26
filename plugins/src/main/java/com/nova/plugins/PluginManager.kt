package com.nova.plugins

enum class PluginCategory {
  MINI_APP, BOT, AI_TOOL, THEME_PACK, STICKERS, AUTOMATION
}

interface NovaPlugin {
  val id: String
  val name: String
  val version: String
  val category: PluginCategory
  fun onEnable()
  fun onDisable()
}

/**
 * NOVA Extensible Plugin System Architecture.
 * Supports third-party Mini-Apps, Bots, AI Custom Tools, Theme Packs, and Automation.
 */
class PluginManager {

  private val registeredPlugins = mutableMapOf<String, NovaPlugin>()
  private val activePluginIds = mutableSetOf<String>()

  fun registerPlugin(plugin: NovaPlugin) {
    registeredPlugins[plugin.id] = plugin
    println("🧩 Registered NOVA Plugin: ${plugin.name} v${plugin.version} [${plugin.category}]")
  }

  fun enablePlugin(pluginId: String): Boolean {
    val plugin = registeredPlugins[pluginId] ?: return false
    plugin.onEnable()
    activePluginIds.add(pluginId)
    return true
  }

  fun disablePlugin(pluginId: String): Boolean {
    val plugin = registeredPlugins[pluginId] ?: return false
    plugin.onDisable()
    activePluginIds.remove(pluginId)
    return true
  }

  fun getActivePlugins(): List<NovaPlugin> {
    return activePluginIds.mapNotNull { registeredPlugins[it] }
  }
}
