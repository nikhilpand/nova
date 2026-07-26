package com.nova.communities

import com.example.nova.data.Community
import com.example.nova.data.CommunityChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * Community & Guild Management Engine.
 * Supports Discord-style roles, text channels, voice rooms, forums, and announcements.
 */
class CommunityManager {

  private val _communities = MutableStateFlow<List<Community>>(emptyList())
  val communities: StateFlow<List<Community>> = _communities.asStateFlow()

  fun createCommunity(name: String): Community {
    val newComm = Community(
      id = UUID.randomUUID().toString(),
      name = name,
      memberCount = 1,
      channels = listOf(
        CommunityChannel(UUID.randomUUID().toString(), "welcome", "announcement"),
        CommunityChannel(UUID.randomUUID().toString(), "general-chat", "text"),
        CommunityChannel(UUID.randomUUID().toString(), "voice-lounge", "voice")
      )
    )
    _communities.value = _communities.value + newComm
    return newComm
  }

  fun addChannel(communityId: String, channelName: String, type: String = "text") {
    _communities.value = _communities.value.map { comm ->
      if (comm.id == communityId) {
        val newChannel = CommunityChannel(UUID.randomUUID().toString(), channelName, type)
        comm.copy(channels = comm.channels + newChannel)
      } else comm
    }
  }
}
