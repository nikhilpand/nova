package com.example.nova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.nova.theme.NOVATheme
import com.example.nova.theme.NovaThemePreset
import com.example.nova.ui.main.MainScreen
import com.example.nova.ui.onboarding.OnboardingScreen
import com.example.nova.ui.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

enum class AppAuthState {
  SPLASH, ONBOARDING, MAIN
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      var appState by remember { mutableStateOf(AppAuthState.SPLASH) }
      var currentPreset by remember { mutableStateOf(NovaThemePreset.DARK) }

      NOVATheme(preset = currentPreset, dynamicColor = false) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          when (appState) {
            AppAuthState.SPLASH -> {
              SplashScreen(onSplashFinished = { appState = AppAuthState.ONBOARDING })
            }

            AppAuthState.ONBOARDING -> {
              OnboardingScreen(
                onOnboardingComplete = { handle, name ->
                  appState = AppAuthState.MAIN
                }
              )
            }

            AppAuthState.MAIN -> {
              MainScreen(
                currentPreset = currentPreset,
                onSelectPreset = { preset -> currentPreset = preset }
              )
            }
          }
        }
      }
    }
  }
}
