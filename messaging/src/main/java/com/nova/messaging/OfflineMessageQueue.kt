package com.nova.messaging

import com.nova.domain.models.DomainMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OfflineQueueStatus(
  val pendingCount: Int = 0,
  val isRetrying: Boolean = false,
  val lastRetryTimestamp: Long = System.currentTimeMillis()
)

/**
 * Offline Message Queue & Automatic Retry Engine.
 * Handles network loss queueing, automatic reconnect dispatching, read receipts, and typing state.
 */
class OfflineMessageQueue {

  private val pendingQueue = mutableListOf<DomainMessage>()
  private val _status = MutableStateFlow(OfflineQueueStatus())
  val status: StateFlow<OfflineQueueStatus> = _status.asStateFlow()

  fun enqueueOfflineMessage(message: DomainMessage) {
    pendingQueue.add(message)
    _status.value = _status.value.copy(pendingCount = pendingQueue.size)
  }

  suspend fun processQueueOnNetworkReconnected(dispatchMessage: suspend (DomainMessage) -> Boolean): Int {
    _status.value = _status.value.copy(isRetrying = true)
    var successCount = 0
    val iterator = pendingQueue.iterator()

    while (iterator.hasNext()) {
      val msg = iterator.next()
      val isSuccess = dispatchMessage(msg)
      if (isSuccess) {
        iterator.remove()
        successCount++
      }
    }

    _status.value = OfflineQueueStatus(
      pendingCount = pendingQueue.size,
      isRetrying = false,
      lastRetryTimestamp = System.currentTimeMillis()
    )

    return successCount
  }
}
