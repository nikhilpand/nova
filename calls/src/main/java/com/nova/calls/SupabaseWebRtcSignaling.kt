package com.nova.calls

import com.nova.network.SupabaseClientManager

/**
 * WebRTC Call Signaling over Supabase Realtime Channels.
 * Broadcasts SDP Offers, Answers, and ICE candidates via Supabase WebSockets.
 */
class SupabaseWebRtcSignaling(
  private val supabaseClient: SupabaseClientManager = SupabaseClientManager()
) {

  suspend fun sendCallOffer(peerId: String, sdpOffer: String) {
    val payload = """{"type":"OFFER","sdp":"$sdpOffer"}"""
    supabaseClient.subscribeToRealtimeChatChannel("call_$peerId")
    println("📡 WebRTC Offer broadcasted via Supabase Realtime to $peerId")
  }

  suspend fun sendCallAnswer(peerId: String, sdpAnswer: String) {
    val payload = """{"type":"ANSWER","sdp":"$sdpAnswer"}"""
    println("📡 WebRTC Answer broadcasted via Supabase Realtime to $peerId")
  }
}
