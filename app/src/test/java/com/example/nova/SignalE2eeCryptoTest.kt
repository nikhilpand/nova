package com.example.nova

import com.example.nova.security.CryptoManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignalE2eeCryptoTest {

  private lateinit var cryptoManager: CryptoManager

  @Before
  fun setUp() {
    cryptoManager = CryptoManager()
  }

  @Test
  fun testEncryptAndDecryptMessage() {
    val originalText = "Top secret NOVA Signal ratchet payload"
    val encryptedPayload = cryptoManager.encryptMessage(originalText)

    assertNotNull(encryptedPayload)
    assertTrue(encryptedPayload.isE2eeVerified)

    val decryptedText = cryptoManager.decryptMessage(encryptedPayload)
    assertEquals(originalText, decryptedText)
  }

  @Test
  fun testVerifySafetyNumbers() {
    val myKey = "IdentityKey_Alex"
    val peerKey = "IdentityKey_Sarah"

    val safetyCode = cryptoManager.verifySafetyNumbers(myKey, peerKey)
    assertNotNull(safetyCode)
    assertTrue(safetyCode.contains("-"))
    assertEquals(23, safetyCode.length)
  }
}
