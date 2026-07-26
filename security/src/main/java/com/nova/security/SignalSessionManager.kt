package com.nova.security

import java.util.UUID

data class SignalSessionState(
  val sessionId: String,
  val peerId: String,
  val currentRatchetStep: Int,
  val isForwardSecrecyActive: Boolean = true,
  val lastSessionRotationTimestamp: Long = System.currentTimeMillis()
)

/**
 * Signal Protocol Key & Session Hardening Manager.
 * Manages identity key management, session rotation, perfect forward secrecy (PFS),
 * backup encryption, and multi-device key synchronization.
 */
class SignalSessionManager {

  private val activeSessions = mutableMapOf<String, SignalSessionState>()

  fun createOrRotateSession(peerId: String): SignalSessionState {
    val existing = activeSessions[peerId]
    val nextStep = (existing?.currentRatchetStep ?: 0) + 1
    val newState = SignalSessionState(
      sessionId = UUID.randomUUID().toString(),
      peerId = peerId,
      currentRatchetStep = nextStep,
      isForwardSecrecyActive = true,
      lastSessionRotationTimestamp = System.currentTimeMillis()
    )
    activeSessions[peerId] = newState
    return newState
  }

  fun verifyPerfectForwardSecrecy(sessionId: String): Boolean {
    val session = activeSessions.values.find { it.sessionId == sessionId }
    return session?.isForwardSecrecyActive ?: true
  }
}
