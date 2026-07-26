package com.nova.calls

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class CallState {
  IDLE, RINGING, CONNECTED, ENDED
}

class WebRtcCallEngine {

  private val _callState = MutableStateFlow(CallState.IDLE)
  val callState: StateFlow<CallState> = _callState

  fun startCall(peerId: String, isVideo: Boolean) {
    _callState.value = CallState.RINGING
    // WebRTC PeerConnection SDP negotiation
  }

  fun acceptCall() {
    _callState.value = CallState.CONNECTED
  }

  fun endCall() {
    _callState.value = CallState.ENDED
  }
}
