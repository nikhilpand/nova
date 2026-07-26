package com.example.nova.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nova.data.Message
import com.example.nova.data.MessageType
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface

@Composable
fun NovaTopBar(
  title: String,
  subtitle: String? = null,
  isE2ee: Boolean = true,
  onBackClick: (() -> Unit)? = null,
  onAiClick: (() -> Unit)? = null,
  onSettingsClick: (() -> Unit)? = null
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 8.dp)
      .liquidGlassSurface(shape = RoundedCornerShape(20.dp))
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      if (onBackClick != null) {
        IconButton(onClick = onBackClick) {
          Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
        }
      }

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          if (isE2ee) {
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              Icons.Default.Lock,
              contentDescription = "E2EE Secured",
              tint = Color(0xFF10B981),
              modifier = Modifier.size(16.dp)
            )
          }
        }
        if (subtitle != null) {
          Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      if (onAiClick != null) {
        IconButton(onClick = onAiClick) {
          Icon(Icons.Default.AutoAwesome, contentDescription = "AI Assistant", tint = MaterialTheme.colorScheme.primary)
        }
      }

      if (onSettingsClick != null) {
        IconButton(onClick = onSettingsClick) {
          Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }
    }
  }
}

@Composable
fun NovaBottomBar(
  currentRoute: String,
  onNavigate: (String) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
      .liquidGlassSurface(shape = RoundedCornerShape(32.dp))
      .padding(vertical = 8.dp, horizontal = 12.dp),
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically
  ) {
    NavIconButton(
      icon = Icons.Default.ChatBubble,
      label = "Chats",
      isSelected = currentRoute == "chats",
      onClick = { onNavigate("chats") }
    )
    NavIconButton(
      icon = Icons.Default.Groups,
      label = "Guilds",
      isSelected = currentRoute == "communities",
      onClick = { onNavigate("communities") }
    )
    NavIconButton(
      icon = Icons.Default.AutoAwesome,
      label = "AI Studio",
      isSelected = currentRoute == "ai",
      onClick = { onNavigate("ai") }
    )
    NavIconButton(
      icon = Icons.Default.Person,
      label = "Profile",
      isSelected = currentRoute == "profile",
      onClick = { onNavigate("profile") }
    )
    NavIconButton(
      icon = Icons.Default.Settings,
      label = "Settings",
      isSelected = currentRoute == "settings",
      onClick = { onNavigate("settings") }
    )
  }
}

@Composable
private fun NavIconButton(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val activeColor = MaterialTheme.colorScheme.primary
  val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .clip(CircleShape)
      .clickable(onClick = onClick)
      .padding(horizontal = 12.dp, vertical = 6.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = if (isSelected) activeColor else inactiveColor,
      modifier = Modifier.size(24.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
      color = if (isSelected) activeColor else inactiveColor,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    )
  }
}

@Composable
fun NovaAvatar(
  name: String,
  isOnline: Boolean = true,
  size: Int = 48
) {
  val initial = name.firstOrNull()?.toString()?.uppercase() ?: "N"
  val primaryColor = MaterialTheme.colorScheme.primary

  Box(modifier = Modifier.size(size.dp)) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .clip(CircleShape)
        .background(primaryColor.copy(alpha = 0.2f))
        .border(BorderStroke(1.5.dp, primaryColor), CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = initial,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = (size * 0.4).sp
      )
    }

    if (isOnline) {
      Box(
        modifier = Modifier
          .size((size * 0.3).dp)
          .clip(CircleShape)
          .background(Color(0xFF10B981))
          .border(BorderStroke(2.dp, MaterialTheme.colorScheme.surface), CircleShape)
          .align(Alignment.BottomEnd)
      )
    }
  }
}

@Composable
fun NovaMessageBubble(
  message: Message,
  isFromMe: Boolean,
  onOptionVote: (Int) -> Unit = {},
  onLongClick: () -> Unit = {}
) {
  val bubbleShape = if (isFromMe) {
    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 4.dp)
  } else {
    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 4.dp, bottomEnd = 20.dp)
  }

  val bubbleColor = if (isFromMe) {
    MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
  } else {
    MaterialTheme.colorScheme.surfaceVariant
  }

  val textColor = if (isFromMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

  Column(
    horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    if (!isFromMe) {
      Text(
        text = message.senderName,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
      )
    }

    Box(
      modifier = Modifier
        .widthIn(max = 300.dp)
        .clip(bubbleShape)
        .background(bubbleColor)
        .clickable { onLongClick() }
        .padding(12.dp)
    ) {
      Column {
        when (message.type) {
          MessageType.CODE_SNIPPET -> {
            Text(
              text = "💻 CODE [${message.codeLanguage ?: "snippet"}]",
              style = MaterialTheme.typography.labelSmall,
              color = Color(0xFFF59E0B),
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF0F172A))
                .padding(8.dp)
            ) {
              Text(
                text = message.content,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = Color(0xFF38BDF8)
              )
            }
          }

          MessageType.POLL -> {
            Text(
              text = "📊 POLL: ${message.content}",
              fontWeight = FontWeight.Bold,
              color = textColor,
              style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            message.pollOptions?.forEach { option ->
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(if (option.isVotedByMe) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.2f))
                  .clickable { onOptionVote(option.id) }
                  .padding(horizontal = 12.dp, vertical = 8.dp)
              ) {
                Icon(
                  if (option.isVotedByMe) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                  contentDescription = null,
                  tint = if (option.isVotedByMe) MaterialTheme.colorScheme.primary else textColor.copy(alpha = 0.6f),
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option.text, color = textColor, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                Text(text = "${option.votes}", color = textColor.copy(alpha = 0.7f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
            }
          }

          else -> {
            Text(
              text = message.content,
              style = MaterialTheme.typography.bodyMedium,
              color = textColor
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Row(
          horizontalArrangement = Arrangement.End,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.align(Alignment.End)
        ) {
          if (message.isE2ee) {
            Icon(
              Icons.Default.Lock,
              contentDescription = "E2EE",
              tint = textColor.copy(alpha = 0.6f),
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
          }
          Text(
            text = "19:15",
            fontSize = 10.sp,
            color = textColor.copy(alpha = 0.6f)
          )
        }
      }
    }
  }
}
