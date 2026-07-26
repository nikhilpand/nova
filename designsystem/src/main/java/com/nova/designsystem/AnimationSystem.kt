package com.nova.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * NOVA Animation System transition specs and entry/exit wrappers.
 */
object AnimationSystem {

  @Composable
  fun AnimatedItemEntry(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
  ) {
    AnimatedVisibility(
      visible = visible,
      enter = fadeIn() + slideInVertically(initialOffsetY = { 40 }),
      exit = fadeOut() + slideOutVertically(targetOffsetY = { 40 }),
      modifier = modifier
    ) {
      content()
    }
  }
}
