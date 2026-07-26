package com.nova.communities

import com.nova.domain.models.DomainCommunity
import com.nova.domain.models.DomainCommunityChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * Community & Guild Management Engine.
 * Supports Discord-style roles, text channels, voice rooms, forums, and announcements.
 */
class CommunityManager {

  private val _communities = MutableStateFlow<List<DomainCommunity>>(emptyList())
  val communities: StateFlow<List<DomainCommunity>> = _communities.asStateFlow()

  fun createCommunity(name: String): DomainCommunity {
    val newComm = DomainCommunity(
      id = UUID.randomUUID().toString(),
      name = name,
      memberCount = 1,
      channels = listOf(
        DomainCommunityChannel(UUID.randomUUID().toString(), "welcome", "announcement"),
        DomainCommunityChannel(UUID.randomUUID().toString(), "general-chat", "text"),
        DomainCommunityChannel(UUID.randomUUID().toString(), "voice-lounge", "voice")
      )
    )
    _communities.value = _communities.value + newComm
    return newComm
  }

  fun addChannel(communityId: String, channelName: String, type: String = "text") {
    _communities.value = _communities.value.map { comm ->
      if (comm.id == communityId) {
        val newChannel = DomainCommunityChannel(UUID.randomUUID().toString(), channelName, type)
        comm.copy(channels = comm.channels + newChannel)
      } else comm
    }
  }
}
