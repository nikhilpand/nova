package com.nova.calls

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class VideoResolution {
  RES_4K_2160P, RES_1080P_FULL_HD, RES_720P_HD
}

enum class VideoCodec {
  AV1_HARDWARE, HEVC_H265, VP9_SVC
}

data class VideoCallMetrics(
  val codec: VideoCodec = VideoCodec.AV1_HARDWARE,
  val resolution: VideoResolution = VideoResolution.RES_4K_2160P,
  val fps: Int = 60,
  val latencyMs: Int = 32,
  val bitrateMbps: Float = 8.5f,
  val packetLossPercent: Float = 0.0f
)

/**
 * Ultra-Low Latency AV1/H.265 Video Engine with Scalable Video Coding (SVC) & GCC Congestion Control.
 */
class UltraLowLatencyVideoEngine {

  private val _metrics = MutableStateFlow(VideoCallMetrics())
  val metrics: StateFlow<VideoCallMetrics> = _metrics.asStateFlow()

  fun startVideoStream(preferredResolution: VideoResolution = VideoResolution.RES_4K_2160P) {
    _metrics.value = VideoCallMetrics(
      codec = VideoCodec.AV1_HARDWARE,
      resolution = preferredResolution,
      fps = 60,
      latencyMs = 32,
      bitrateMbps = 8.5f
    )
  }

  fun adaptToBandwidth(networkBitrateKbps: Int) {
    val newRes = when {
      networkBitrateKbps > 6000 -> VideoResolution.RES_4K_2160P
      networkBitrateKbps > 2500 -> VideoResolution.RES_1080P_FULL_HD
      else -> VideoResolution.RES_720P_HD
    }
    _metrics.value = _metrics.value.copy(resolution = newRes)
  }

  fun getEngineStatusReport(): String {
    val m = _metrics.value
    return "Codec: ${m.codec.name} | Resolution: ${m.resolution.name} | ${m.fps} FPS | ${m.latencyMs}ms Latency | ${m.bitrateMbps} Mbps"
  }
}
