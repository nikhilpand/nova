package com.nova.extensions

/**
 * First-party NOVA Extension Registry.
 * Houses built-in extensions that ship with the app (Stories, Bots Marketplace, Polls, etc.)
 */
object ExtensionRegistry {

  data class Extension(
    val id: String,
    val name: String,
    val isBuiltIn: Boolean = true,
    val isEnabled: Boolean = true
  )

  val builtInExtensions = listOf(
    Extension("ext_stories", "Stories & Status Updates"),
    Extension("ext_polls", "Interactive Polls & Quizzes"),
    Extension("ext_canvas", "Whiteboard Canvas Drawing"),
    Extension("ext_code_blocks", "Syntax-Highlighted Code Blocks"),
    Extension("ext_voice_notes", "Waveform Voice Note Player"),
    Extension("ext_disappearing", "Disappearing Messages Timer")
  )

  fun getEnabledExtensions(): List<Extension> = builtInExtensions.filter { it.isEnabled }
}
