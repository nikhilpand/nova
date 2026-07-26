package com.nova.themeapi

/**
 * NOVA Theme Pack API.
 * Allows third-party theme packs to register custom color palettes,
 * typography scales, blur configurations, and shader files.
 */
object ThemePackApi {

  data class ThemePack(
    val id: String,
    val name: String,
    val author: String,
    val primaryHex: String,
    val surfaceHex: String,
    val isDark: Boolean,
    val blurRadius: Float = 24f,
    val cornerRadius: Float = 20f
  )

  private val installedThemes = mutableListOf<ThemePack>()

  fun registerThemePack(theme: ThemePack) {
    installedThemes.add(theme)
    println("🎨 Registered theme pack: ${theme.name} by ${theme.author}")
  }

  fun getAvailableThemes(): List<ThemePack> = installedThemes.toList()
}
