package com.nova.messaging

import com.nova.domain.models.DomainMessage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class OfflineMessageQueueTest {

  private lateinit var queue: OfflineMessageQueue

  @Before
  fun setUp() {
    queue = OfflineMessageQueue()
  }

  @Test
  fun testEnqueueOfflineMessage() {
    val msg = DomainMessage(
      conversationId = "conv_1",
      senderId = "u1",
      senderName = "Alex",
      content = "Offline message payload"
    )

    queue.enqueueOfflineMessage(msg)
    val status = queue.status.value

    assertEquals(1, status.pendingCount)
    assertFalse(status.isRetrying)
  }

  @Test
  fun testProcessQueueOnNetworkReconnected() = runBlocking {
    val msg1 = DomainMessage(conversationId = "c1", senderId = "u1", senderName = "A", content = "M1")
    val msg2 = DomainMessage(conversationId = "c1", senderId = "u1", senderName = "A", content = "M2")

    queue.enqueueOfflineMessage(msg1)
    queue.enqueueOfflineMessage(msg2)

    val successCount = queue.processQueueOnNetworkReconnected { true }

    assertEquals(2, successCount)
    assertEquals(0, queue.status.value.pendingCount)
  }
}
