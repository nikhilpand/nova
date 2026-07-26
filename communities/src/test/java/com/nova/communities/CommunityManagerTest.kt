package com.nova.communities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CommunityManagerTest {

  private lateinit var communityManager: CommunityManager

  @Before
  fun setUp() {
    communityManager = CommunityManager()
  }

  @Test
  fun testCreateCommunity() {
    val comm = communityManager.createCommunity("Android AI Guild")

    assertNotNull(comm)
    assertEquals("Android AI Guild", comm.name)
    assertEquals(3, comm.channels.size)
    assertEquals("welcome", comm.channels[0].name)
  }

  @Test
  fun testAddChannel() {
    val comm = communityManager.createCommunity("NOVA Devs")
    communityManager.addChannel(comm.id, "voice-lounge-2", "voice")

    val updatedComm = communityManager.communities.value.find { it.id == comm.id }
    assertNotNull(updatedComm)
    assertEquals(4, updatedComm?.channels?.size)
  }
}
