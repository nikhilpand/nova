package com.example.nova.ui.calls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.theme.liquidGlassSurface
import com.nova.calls.LowBandwidthResilienceEngine
import com.nova.calls.NetworkQualityTier
import com.nova.calls.Opus4KAudioEngine
import com.nova.calls.UltraLowLatencyVideoEngine

@Composable
fun CallScreen(
  peerName: String = "Sarah Connor",
  onEndCall: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val audioEngine = remember(context) { Opus4KAudioEngine(context) }
  val videoEngine = remember { UltraLowLatencyVideoEngine() }
  val resilienceEngine = remember { LowBandwidthResilienceEngine() }

  var isMuted by remember { mutableStateOf(false) }
  var isVideoEnabled by remember { mutableStateOf(true) }
  var isScreenSharing by remember { mutableStateOf(false) }
  var isAiNoiseSuppressionOn by remember { mutableStateOf(true) }

  val videoMetrics by videoEngine.metrics.collectAsState()
  val resilienceStatus by resilienceEngine.status.collectAsState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFF0A0014))
  ) {
    // 4K Video Stream or Audio-First Fallback Viewport Simulation
    if (isVideoEnabled && !resilienceStatus.isAudioOnlyFallback) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(0xFF1E1B4B)),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(Icons.Default.Videocam, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(72.dp))
          Spacer(modifier = Modifier.height(12.dp))
          Text(text = "AV1 Video Stream (${resilienceStatus.activeVideoMode})", color = Color.White, fontWeight = FontWeight.Bold)
          Text(text = "Low Net Engine: ${resilienceStatus.networkTier.name}", color = Color(0xFF38BDF8), fontSize = 12.sp)
        }
      }
    } else {
      // Audio-First Low Network Mode Viewport
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(if (resilienceStatus.isAudioOnlyFallback) Color(0xFF451A03) else Color(0xFF0F172A)),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            if (resilienceStatus.isAudioOnlyFallback) Icons.Default.SignalCellularConnectedNoInternet0Bar else Icons.Default.Mic,
            contentDescription = null,
            tint = if (resilienceStatus.isAudioOnlyFallback) Color(0xFFF59E0B) else Color(0xFF10B981),
            modifier = Modifier.size(80.dp)
          )
          Spacer(modifier = Modifier.height(16.dp))
          Text(text = peerName, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(6.dp))
          Text(text = resilienceStatus.activeAudioMode, color = Color(0xFFF59E0B), fontWeight = FontWeight.SemiBold)
          if (resilienceStatus.packetLossConcealmentActive) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "⚡ AI Speech Packet Loss Concealment (PLC) Active", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
          }
        }
      }
    }

    // Top HUD Stats Overlay Card with Network Simulation Controls
    Column(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = 40.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .liquidGlassSurface(shape = RoundedCornerShape(20.dp))
          .padding(14.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            Icons.Default.Speed,
            contentDescription = null,
            tint = if (resilienceStatus.isAudioOnlyFallback) Color(0xFFF59E0B) else Color(0xFF10B981),
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(text = "$peerName • ${resilienceStatus.networkTier.name}", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Text(
              text = "${resilienceStatus.activeAudioMode} | ${resilienceStatus.activeVideoMode}",
              color = Color(0xFF38BDF8),
              fontSize = 11.sp
            )
          }
          AssistChip(
            onClick = {},
            label = { Text("E2EE AES-256", fontSize = 10.sp, color = Color(0xFF10B981)) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(12.dp)) }
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Network Simulator Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        AssistChip(
          onClick = { resilienceEngine.processNetworkStats(8000, 0f, 15) },
          label = { Text("5G/WiFi", fontSize = 10.sp) }
        )
        AssistChip(
          onClick = { resilienceEngine.processNetworkStats(1500, 5f, 60) },
          label = { Text("4G Fair", fontSize = 10.sp) }
        )
        AssistChip(
          onClick = { resilienceEngine.processNetworkStats(300, 25f, 180) },
          label = { Text("3G Weak", fontSize = 10.sp) }
        )
        AssistChip(
          onClick = { resilienceEngine.processNetworkStats(40, 55f, 450) },
          label = { Text("2G Low Net", fontSize = 10.sp) }
        )
      }
    }

    // Glass Control Bar at Bottom
    Row(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 36.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth()
        .liquidGlassSurface(shape = RoundedCornerShape(36.dp))
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Mute Audio
      IconButton(
        onClick = { isMuted = audioEngine.toggleMute() },
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(if (isMuted) Color(0xFFEF4444) else Color.White.copy(alpha = 0.15f))
      ) {
        Icon(if (isMuted) Icons.Default.MicOff else Icons.Default.Mic, contentDescription = "Mute", tint = Color.White)
      }

      // Toggle Video
      IconButton(
        onClick = { isVideoEnabled = !isVideoEnabled },
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(if (!isVideoEnabled) Color(0xFFEF4444) else Color.White.copy(alpha = 0.15f))
      ) {
        Icon(if (isVideoEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff, contentDescription = "Video", tint = Color.White)
      }

      // Screen Share
      IconButton(
        onClick = { isScreenSharing = !isScreenSharing },
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(if (isScreenSharing) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.15f))
      ) {
        Icon(Icons.Default.ScreenShare, contentDescription = "Screen Share", tint = Color.White)
      }

      // AI Noise Suppression
      IconButton(
        onClick = { isAiNoiseSuppressionOn = !isAiNoiseSuppressionOn },
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(if (isAiNoiseSuppressionOn) Color(0xFF10B981) else Color.White.copy(alpha = 0.15f))
      ) {
        Icon(Icons.Default.GraphicEq, contentDescription = "Noise Suppression", tint = Color.White)
      }

      // End Call Button
      IconButton(
        onClick = onEndCall,
        modifier = Modifier
          .size(54.dp)
          .clip(CircleShape)
          .background(Color(0xFFEF4444))
      ) {
        Icon(Icons.Default.CallEnd, contentDescription = "End Call", tint = Color.White)
      }
    }
  }
}
