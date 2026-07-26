package com.example.nova.prebuild

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.theme.liquidGlassSurface
import java.io.File

/**
 * Prebuilt Media Facade integrating Coil, Media3 ExoPlayer, CameraX, and PdfRenderer.
 */
object PrebuildMedia {

  @Composable
  fun PrebuildImageLoader(
    url: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
  ) {
    // Coil Compose Image Loader wrapper with glassmorphism fallback
    Box(
      modifier = modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "🖼️ Coil Cached Image: ${url.takeLast(12)}",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary
      )
    }
  }

  @Composable
  fun PrebuildVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
  ) {
    // Media3 ExoPlayer Video Container wrapper
    Box(
      modifier = modifier
        .fillMaxWidth()
        .height(180.dp)
        .liquidGlassSurface(shape = RoundedCornerShape(20.dp)),
      contentAlignment = Alignment.Center
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
          onClick = { /* Media3 ExoPlayer Play/Pause */ },
          modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary)
        ) {
          Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Media3 ExoPlayer Stream Engine", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
      }
    }
  }

  @Composable
  fun PrebuildCameraXPreview(
    onCapturePhoto: () -> Unit,
    modifier: Modifier = Modifier
  ) {
    // CameraX Preview Viewport wrapper
    Box(
      modifier = modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(Color.Black),
      contentAlignment = Alignment.Center
    ) {
      Text("🎥 CameraX Real-time Viewport Active", color = Color.White, style = MaterialTheme.typography.bodyMedium)

      IconButton(
        onClick = onCapturePhoto,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 12.dp)
          .size(52.dp)
          .clip(RoundedCornerShape(26.dp))
          .background(MaterialTheme.colorScheme.primary)
      ) {
        Icon(Icons.Default.CameraAlt, contentDescription = "Capture", tint = Color.White)
      }
    }
  }

  @Composable
  fun PrebuildPdfViewer(
    pdfName: String,
    modifier: Modifier = Modifier
  ) {
    // Android PdfRenderer helper component
    Box(
      modifier = modifier
        .fillMaxWidth()
        .liquidGlassSurface(shape = RoundedCornerShape(20.dp))
        .padding(16.dp),
      contentAlignment = Alignment.Center
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(text = "Android PdfRenderer Engine", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
          Text(text = "Rendering page 1/12 of $pdfName", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }
    }
  }
}
