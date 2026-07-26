package com.example.nova.theme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween

object NovaMotion {
  // Motion Durations as specified in NOVA Project Bible Section 18
  const val DURATION_FAST_MS = 120
  const val DURATION_NORMAL_MS = 220
  const val DURATION_LARGE_MS = 350
  const val DURATION_HERO_MS = 500

  // Easing Curves
  val EmphasizedEasing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
  val ExpressiveEasing = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1.0f)

  // Spring Physics
  fun <T> bouncySpring(): AnimationSpec<T> = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow
  )

  fun <T> smoothSpring(): AnimationSpec<T> = spring(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMedium
  )

  fun <T> fastTween(): AnimationSpec<T> = tween(
    durationMillis = DURATION_FAST_MS,
    easing = FastOutSlowInEasing
  )

  fun <T> normalTween(): AnimationSpec<T> = tween(
    durationMillis = DURATION_NORMAL_MS,
    easing = EmphasizedEasing
  )

  fun <T> heroTween(): AnimationSpec<T> = tween(
    durationMillis = DURATION_HERO_MS,
    easing = ExpressiveEasing
  )
}
