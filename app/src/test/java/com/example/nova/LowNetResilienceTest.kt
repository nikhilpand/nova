package com.example.nova

import com.nova.calls.LowBandwidthResilienceEngine
import com.nova.calls.NetworkQualityTier
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LowNetResilienceTest {

  private lateinit var resilienceEngine: LowBandwidthResilienceEngine

  @Before
  fun setUp() {
    resilienceEngine = LowBandwidthResilienceEngine()
  }

  @Test
  fun test5gWiFiNetworkMode() {
    resilienceEngine.processNetworkStats(bitrateKbps = 8000, packetLossPercent = 0f, rttMs = 12)
    val status = resilienceEngine.status.value

    assertEquals(NetworkQualityTier.EXCELLENT_5G_WIFI, status.networkTier)
    assertTrue(status.activeVideoMode.contains("4K"))
    assertTrue(status.activeAudioMode.contains("48kHz"))
  }

  @Test
  fun test2gExtremeLowNetworkFallback() {
    resilienceEngine.processNetworkStats(bitrateKbps = 40, packetLossPercent = 50f, rttMs = 450)
    val status = resilienceEngine.status.value

    assertEquals(NetworkQualityTier.EXTREME_2G_LOW_NET, status.networkTier)
    assertTrue(status.isAudioOnlyFallback)
    assertTrue(status.packetLossConcealmentActive)
    assertEquals(3, status.opusRedundancyMultiplier)
  }
}
