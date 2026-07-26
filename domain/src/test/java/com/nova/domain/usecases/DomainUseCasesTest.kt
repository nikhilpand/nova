package com.nova.domain.usecases

import com.nova.domain.models.DomainMessageType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DomainUseCasesTest {

  @Test
  fun testSendMessageUseCase() {
    val useCase = SendMessageUseCase()
    val msg = useCase(
      conversationId = "c100",
      senderId = "u1",
      senderName = "Alex",
      text = "Hello Signal E2EE",
      type = DomainMessageType.TEXT
    )

    assertNotNull(msg.id)
    assertEquals("c100", msg.conversationId)
    assertEquals("Hello Signal E2EE", msg.content)
    assertTrue(msg.isE2ee)
  }

  @Test
  fun testVerifyE2eeSafetyNumberUseCase() {
    val useCase = VerifyE2eeSafetyNumberUseCase()
    val safetyNumber = useCase("KeyA_12345", "KeyB_67890")

    assertNotNull(safetyNumber)
    assertEquals(23, safetyNumber.length)
    assertTrue(safetyNumber.contains("-"))
  }

  @Test
  fun testGenerateSmartReplyUseCase() {
    val useCase = GenerateSmartReplyUseCase()
    val replies = useCase("The signal keys are verified")

    assertTrue(replies.isNotEmpty())
    assertTrue(replies[0].contains("E2EE"))
  }
}
