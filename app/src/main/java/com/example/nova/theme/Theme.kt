package com.example.nova.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

enum class NovaThemePreset {
  AMOLED, DARK, LIGHT, GLASS, CYBERPUNK, NATURE
}

val AmoledColorScheme = darkColorScheme(
  primary = Color(0xFF7C4DFF),
  onPrimary = Color.White,
  primaryContainer = Color(0xFF311B92),
  onPrimaryContainer = Color(0xFFD1C4E9),
  secondary = Color(0xFF00E676),
  onSecondary = Color.Black,
  background = Color.Black,
  onBackground = Color.White,
  surface = Color(0xFF121212),
  onSurface = Color.White,
  surfaceVariant = Color(0xFF1E1E1E),
  onSurfaceVariant = Color(0xFFCCCCCC)
)

val DarkColorScheme = darkColorScheme(
  primary = Color(0xFF6366F1),
  onPrimary = Color.White,
  primaryContainer = Color(0xFF3730A3),
  onPrimaryContainer = Color(0xFFE0E7FF),
  secondary = Color(0xFF10B981),
  onSecondary = Color.Black,
  background = Color(0xFF0F172A),
  onBackground = Color(0xFFF8FAFC),
  surface = Color(0xFF1E293B),
  onSurface = Color(0xFFF8FAFC),
  surfaceVariant = Color(0xFF334155),
  onSurfaceVariant = Color(0xFF94A3B8)
)

val LightColorScheme = lightColorScheme(
  primary = Color(0xFF4F46E5),
  onPrimary = Color.White,
  primaryContainer = Color(0xFFEEF2FF),
  onPrimaryContainer = Color(0xFF312E81),
  secondary = Color(0xFF059669),
  onSecondary = Color.White,
  background = Color(0xFFF8FAFC),
  onBackground = Color(0xFF0F172A),
  surface = Color.White,
  onSurface = Color(0xFF0F172A),
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = Color(0xFF64748B)
)

val CyberpunkColorScheme = darkColorScheme(
  primary = Color(0xFFFF007F),
  onPrimary = Color.White,
  primaryContainer = Color(0xFF50002A),
  onPrimaryContainer = Color(0xFFFFB3DA),
  secondary = Color(0xFF00F0FF),
  onSecondary = Color.Black,
  background = Color(0xFF0A0014),
  onBackground = Color(0xFF00F0FF),
  surface = Color(0xFF1D0033),
  onSurface = Color(0xFFFF007F),
  surfaceVariant = Color(0xFF2E004F),
  onSurfaceVariant = Color(0xFFFF80BF)
)

val NatureColorScheme = darkColorScheme(
  primary = Color(0xFF10B981),
  onPrimary = Color.Black,
  primaryContainer = Color(0xFF064E3B),
  onPrimaryContainer = Color(0xFFA7F3D0),
  secondary = Color(0xFFF59E0B),
  onSecondary = Color.Black,
  background = Color(0xFF061A14),
  onBackground = Color(0xFFECFDF5),
  surface = Color(0xFF0B2E24),
  onSurface = Color(0xFFECFDF5),
  surfaceVariant = Color(0xFF134E3A),
  onSurfaceVariant = Color(0xFF6EE7B7)
)

val LocalThemePreset = staticCompositionLocalOf { NovaThemePreset.DARK }

@Composable
fun NOVATheme(
  preset: NovaThemePreset = NovaThemePreset.DARK,
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit
) {
  val context = LocalContext.current
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    preset == NovaThemePreset.AMOLED -> AmoledColorScheme
    preset == NovaThemePreset.CYBERPUNK -> CyberpunkColorScheme
    preset == NovaThemePreset.NATURE -> NatureColorScheme
    preset == NovaThemePreset.LIGHT -> LightColorScheme
    else -> DarkColorScheme
  }

  CompositionLocalProvider(LocalThemePreset provides preset) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}
