package com.nova.auth

import io.github.jan_tennert.supabase.SupabaseClient
import io.github.jan_tennert.supabase.auth.auth

data class GitHubProfile(
    val id: String,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val bio: String
)

/**
 * GitHub OAuth 2.0 & Supabase Auth Synchronizer for NOVA.
 * Handles OAuth authorize URL generation and Supabase OAuth sign-in.
 */
class GitHubAuthManager(private val supabaseClient: SupabaseClient? = null) {

    fun initiateGitHubOAuth(clientId: String, redirectUri: String = "nova://auth/github/callback"): String {
        return "https://github.com/login/oauth/authorize?client_id=$clientId&redirect_uri=$redirectUri&scope=read:user"
    }

    suspend fun signInWithGitHubOAuth(): Boolean {
        val client = supabaseClient ?: return false
        return try {
            client.auth.currentSessionOrNull() != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
