package com.nova.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive

data class WebSocketFramePayload(
    val event: String,
    val payloadJson: String,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * High-Performance Ktor WebSocket & WebRTC Signaling Client.
 * Connects via WebSocketSession using Ktor CIO engine.
 */
class KtorNetworkClient {

    private val client = HttpClient(CIO) {
        install(WebSockets)
    }

    private var session: WebSocketSession? = null
    private val _incomingFrames = MutableSharedFlow<WebSocketFramePayload>()
    val incomingFrames: SharedFlow<WebSocketFramePayload> = _incomingFrames.asSharedFlow()

    suspend fun connectWebSocket(endpointUrl: String = "wss://api.nova.app/v1/ws") {
        try {
            session = client.webSocketSession(endpointUrl)
            val currentSession = session ?: return
            while (currentSession.isActive) {
                val frame = currentSession.incoming.receive()
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    _incomingFrames.emit(WebSocketFramePayload(event = "MESSAGE", payloadJson = text))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sendFrame(event: String, payloadJson: String) {
        val currentSession = session
        if (currentSession != null && currentSession.isActive) {
            currentSession.send(Frame.Text("""{"event":"$event","payload":$payloadJson}"""))
        }
    }

    fun createWebRtcOfferSignal(peerId: String, sdpOffer: String): String {
        return """
            {
                "event": "WEBRTC_OFFER",
                "peerId": "$peerId",
                "sdp": "$sdpOffer"
            }
        """.trimIndent()
    }

    fun close() {
        client.close()
    }
}
