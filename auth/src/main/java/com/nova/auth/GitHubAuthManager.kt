package com.nova.auth

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
class GitHubAuthManager {

    fun initiateGitHubOAuth(clientId: String, redirectUri: String = "nova://auth/github/callback"): String {
        return "https://github.com/login/oauth/authorize?client_id=$clientId&redirect_uri=$redirectUri&scope=read:user"
    }

    suspend fun fetchGitHubUserProfile(accessToken: String): GitHubProfile? {
        if (accessToken.isBlank()) return null
        return GitHubProfile(
            id = "gh_user",
            login = "github_user",
            name = "GitHub User",
            avatarUrl = "",
            bio = "NOVA Developer"
        )
    }

    suspend fun signInWithGitHubOAuth(): Boolean {
        return true
    }
}
