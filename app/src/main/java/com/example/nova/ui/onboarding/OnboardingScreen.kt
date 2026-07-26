package com.example.nova.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface

@Composable
fun OnboardingScreen(
  onOnboardingComplete: (String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  var step by remember { mutableStateOf(1) }
  var usernameInput by remember { mutableStateOf("") }
  var displayNameInput by remember { mutableStateOf("") }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFF0F172A)),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
    ) {
      if (step == 1) {
        // Step 1: Passkeys & Biometrics Onboarding
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .liquidGlassSurface(shape = RoundedCornerShape(24.dp)),
          colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
          ) {
            Icon(Icons.Default.Fingerprint, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Passwordless Onboarding", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "NOVA uses Signal E2EE and WebAuthn Passkeys. No phone number required.",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
              onClick = { step = 2 },
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text("Create Passkey Identity")
              Spacer(modifier = Modifier.width(8.dp))
              Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
          }
        }
      } else {
        // Step 2: Username & Display Name Setup
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .glassmorphism(shape = RoundedCornerShape(24.dp)),
          colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
          Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Claim Your Handle", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Set your public username and display name.", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.7f))

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
              value = displayNameInput,
              onValueChange = { displayNameInput = it },
              label = { Text("Display Name") },
              leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
              value = usernameInput,
              onValueChange = { usernameInput = it.lowercase().replace(" ", "_") },
              label = { Text("Username Handle (@username)") },
              leadingIcon = { Text("@", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 12.dp)) },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
              onClick = {
                val handle = usernameInput.ifBlank { "nova_user" }
                val name = displayNameInput.ifBlank { "Nova Explorer" }
                onOnboardingComplete(handle, name)
              },
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text("Launch NOVA Messenger")
            }
          }
        }
      }
    }
  }
}
