package com.nova.network

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class SupabaseClientManagerTest {

  private lateinit var manager: SupabaseClientManager

  @Before
  fun setUp() {
    manager = SupabaseClientManager()
  }

  @Test
  fun testDefaultEndpointAndCredentials() {
    assertEquals("https://oqqqzdhxwpqsholcfdsg.supabase.co", SupabaseClientManager.DEFAULT_URL)
    assertNotNull(SupabaseClientManager.DEFAULT_ANON_KEY)
  }

  @Test
  fun testGetStorageUploadUrl() {
    val url = manager.getStorageUploadUrl("avatars", "user_1.png")
    assertNotNull(url)
    assertEquals("https://oqqqzdhxwpqsholcfdsg.supabase.co/storage/v1/object/public/avatars/user_1.png", url)
  }
}
