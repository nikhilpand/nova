package com.nova.auth

import android.content.Context
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPublicKeyCredentialOption

/**
 * Production Passkey & Biometric Authentication Manager.
 * Utilizes AndroidX CredentialManager API for WebAuthn passkey registration and authentication.
 */
class PasskeyAuthManager(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    fun createRegisterRequestJson(username: String, challengeBase64: String): String {
        return """
            {
                "rp": {"name": "NOVA Platform", "id": "nova.app"},
                "user": {
                    "id": "${username.hashCode()}",
                    "name": "$username",
                    "displayName": "$username"
                },
                "challenge": "$challengeBase64",
                "pubKeyCredParams": [{"type": "public-key", "alg": -7}],
                "timeout": 60000,
                "attestation": "direct"
            }
        """.trimIndent()
    }

    suspend fun registerPasskey(requestJson: String): String? {
        return try {
            val request = CreatePublicKeyCredentialRequest(requestJson)
            val response = credentialManager.createCredential(context, request)
            response.data.getString("androidx.credentials.BUNDLE_KEY_REGISTRATION_RESPONSE_JSON")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun authenticateWithPasskey(requestJson: String): String? {
        return try {
            val getOption = GetPublicKeyCredentialOption(requestJson)
            val getRequest = GetCredentialRequest.Builder()
                .addCredentialOption(getOption)
                .build()

            val result = credentialManager.getCredential(context, getRequest)
            val credential = result.credential
            credential.data.getString("androidx.credentials.BUNDLE_KEY_AUTHENTICATION_RESPONSE_JSON")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
