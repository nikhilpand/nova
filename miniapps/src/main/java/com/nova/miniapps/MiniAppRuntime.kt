package com.nova.miniapps

import androidx.compose.runtime.Composable

/**
 * NOVA Mini-App Runtime Framework.
 * Allows lightweight Compose-based mini-applications to run inside chat threads.
 */
object MiniAppRuntime {

  data class MiniApp(
    val appId: String,
    val name: String,
    val version: String,
    val description: String,
    val entryPoint: @Composable () -> Unit
  )

  private val installedApps = mutableMapOf<String, MiniApp>()

  fun installMiniApp(app: MiniApp) {
    installedApps[app.appId] = app
    println("📱 Installed mini-app: ${app.name} v${app.version}")
  }

  fun uninstallMiniApp(appId: String) {
    installedApps.remove(appId)
  }

  fun getInstalledApps(): List<MiniApp> = installedApps.values.toList()
}
