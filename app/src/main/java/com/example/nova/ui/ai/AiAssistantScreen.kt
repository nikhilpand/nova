package com.example.nova.ui.ai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nova.ai.AiPlatformEngine
import com.example.nova.ai.AiTone
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface
import com.example.nova.ui.components.NovaTopBar

@Composable
fun AiAssistantScreen(
  aiEngine: AiPlatformEngine = remember { AiPlatformEngine() },
  modifier: Modifier = Modifier
) {
  var inputText by remember { mutableStateOf("") }
  var outputResult by remember { mutableStateOf("") }
  var selectedTone by remember { mutableStateOf(AiTone.PROFESSIONAL) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    NovaTopBar(
      title = "NOVA AI Studio",
      subtitle = "On-Device Assistant • Grammar • Tasks • OCR",
      isE2ee = true
    )

    Column(modifier = Modifier.padding(16.dp)) {
      // Input Card
      OutlinedTextField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = { Text("Paste any message, draft, or notes here for AI processing...") },
        modifier = Modifier
          .fillMaxWidth()
          .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
          unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
        )
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Tone Selection Chips
      Text(text = "SELECT AI TONE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      Spacer(modifier = Modifier.height(6.dp))

      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AiTone.values().forEach { tone ->
          FilterChip(
            selected = selectedTone == tone,
            onClick = { selectedTone = tone },
            label = { Text(tone.name.lowercase().replaceFirstChar { it.uppercase() }) },
            shape = RoundedCornerShape(14.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Quick Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = {
            outputResult = aiEngine.rewriteText(inputText.ifBlank { "Meet at 5 PM for Nova code review" }, selectedTone)
          },
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Rewrite")
        }

        OutlinedButton(
          onClick = {
            val tasks = aiEngine.extractActionTasks(inputText.ifBlank { "Verify E2EE ratcheting keys and test 120 FPS Compose physics" })
            outputResult = tasks.joinToString("\n• ", prefix = "📋 Action Items:\n• ")
          },
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.Checklist, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Tasks")
        }

        OutlinedButton(
          onClick = {
            outputResult = "📄 OCR Output: Recognized 120 FPS Compose physics diagram from image."
          },
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.DocumentScanner, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("OCR")
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Output Card
      if (outputResult.isNotBlank()) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .liquidGlassSurface(shape = RoundedCornerShape(20.dp)),
          colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(text = "AI Result Output", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = outputResult, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
          }
        }
      }
    }
  }
}
