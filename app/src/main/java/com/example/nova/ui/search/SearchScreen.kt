package com.example.nova.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nova.theme.glassmorphism
import com.example.nova.theme.liquidGlassSurface

data class SearchResultItem(
  val id: String,
  val title: String,
  val subtitle: String,
  val category: String
)

@Composable
fun SearchScreen(
  onBackClick: () -> Unit,
  onResultClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedFilter by remember { mutableStateOf("ALL") }

  val filters = listOf("ALL", "PEOPLE", "MESSAGES", "MEDIA", "FILES", "GUILDS")

  val mockResults = listOf(
    SearchResultItem("u1", "Sarah Connor (@sarah_connor)", "Verified E2EE • Online • Click to start chat", "PEOPLE"),
    SearchResultItem("u2", "Marcus Vance (@marcus_vance)", "NOVA Core Architect • Online", "PEOPLE"),
    SearchResultItem("s1", "Signal E2EE Session Key Exchange", "Sarah Connor: The keys have been verified...", "MESSAGES"),
    SearchResultItem("s2", "NOVA Architecture Whiteboard.png", "Media attachment (1.4 MB)", "MEDIA"),
    SearchResultItem("s3", "NOVA Project Bible v1.0.pdf", "PDF Document (3.2 MB)", "FILES"),
    SearchResultItem("s4", "NOVA Developer Guild", "1,240 Members • #compose-ui-lab", "GUILDS")
  )

  val filteredResults = mockResults.filter { item ->
    val matchesFilter = selectedFilter == "ALL" || item.category == selectedFilter
    val matchesQuery = searchQuery.isBlank() || item.title.contains(searchQuery, ignoreCase = true) || item.subtitle.contains(searchQuery, ignoreCase = true)
    matchesFilter && matchesQuery
  }

  Column(modifier = modifier.fillMaxSize()) {
    // Search Top Bar
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .liquidGlassSurface(shape = RoundedCornerShape(20.dp))
        .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
      IconButton(onClick = onBackClick) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
      }

      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search messages, media, files, guilds...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
        singleLine = true,
        modifier = Modifier.weight(1f),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = Color.Transparent,
          unfocusedBorderColor = Color.Transparent
        )
      )
    }

    // Filter Row
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(filters) { filter ->
        FilterChip(
          selected = selectedFilter == filter,
          onClick = { selectedFilter = filter },
          label = { Text(filter.lowercase().replaceFirstChar { it.uppercase() }) },
          shape = RoundedCornerShape(14.dp)
        )
      }
    }

    // Results List
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(filteredResults) { res ->
        val icon = when (res.category) {
          "PEOPLE" -> Icons.Default.Person
          "MEDIA" -> Icons.Default.Image
          "FILES" -> Icons.Default.Description
          else -> Icons.Default.Chat
        }

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .glassmorphism(shape = RoundedCornerShape(18.dp))
            .clickable { onResultClick(res.id) },
          colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
          ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = res.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
              Text(text = res.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    }
  }
}
