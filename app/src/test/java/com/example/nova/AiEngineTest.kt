package com.example.nova

import com.example.nova.ai.AiPlatformEngine
import com.example.nova.ai.AiTone
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AiEngineTest {

  private lateinit var aiEngine: AiPlatformEngine

  @Before
  fun setUp() {
    aiEngine = AiPlatformEngine()
  }

  @Test
  fun testGenerateSmartReplies() {
    val replies = aiEngine.generateSmartReplies("The E2EE keys are verified.")
    assertTrue(replies.isNotEmpty())
    assertTrue(replies.any { it.contains("E2EE") })
  }

  @Test
  fun testRewriteTextTone() {
    val text = "Review the Nova architecture code"
    val professional = aiEngine.rewriteText(text, AiTone.PROFESSIONAL)
    assertTrue(professional.contains("discussion"))

    val pirate = aiEngine.rewriteText(text, AiTone.PIRATE)
    assertTrue(pirate.contains("Ahoy") || pirate.contains("Arrr"))
  }

  @Test
  fun testTaskExtraction() {
    val input = "TODO: verify E2EE ratcheting keys\nFIX: test 120 FPS Compose physics"
    val tasks = aiEngine.extractActionTasks(input)

    assertTrue(tasks.size >= 2)
  }
}
