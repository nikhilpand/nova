package com.example.nova.prebuild

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface

/**
 * Prebuilt Security, ZXing, ML Kit OCR, and WebRTC Facades.
 */
object PrebuildSecurityAndQr {

  @Composable
  fun PrebuildQrCodeCard(
    content: String,
    modifier: Modifier = Modifier
  ) {
    // ZXing QR Code generator helper card
    Card(
      modifier = modifier
        .fillMaxWidth()
        .liquidGlassSurface(shape = RoundedCornerShape(20.dp)),
      colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
      ) {
        Icon(Icons.Default.QrCode2, contentDescription = "QR Code", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ZXing Encrypted Identity QR", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        Text(text = content, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  }

  @Composable
  fun PrebuildMlKitOcrCard(
    extractedText: String,
    modifier: Modifier = Modifier
  ) {
    // ML Kit On-Device Text Recognition helper
    Card(
      modifier = modifier
        .fillMaxWidth()
        .glassmorphism(shape = RoundedCornerShape(20.dp)),
      colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.DocumentScanner, contentDescription = null, tint = Color(0xFF10B981))
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "ML Kit On-Device OCR Engine", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = extractedText, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
      }
    }
  }

  @Composable
  fun PrebuildWebRtcCallCard(
    peerName: String,
    onEndCall: () -> Unit,
    modifier: Modifier = Modifier
  ) {
    // WebRTC Voice/Video call engine helper
    Card(
      modifier = modifier
        .fillMaxWidth()
        .liquidGlassSurface(shape = RoundedCornerShape(24.dp)),
      colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
      ) {
        Icon(Icons.Default.Call, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(text = "WebRTC Encrypted Peer Call", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
          Text(text = "Connected with $peerName • 48kHz HD Spatial Audio", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Button(
          onClick = onEndCall,
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
          shape = RoundedCornerShape(14.dp)
        ) {
          Text("End")
        }
      }
    }
  }
}
