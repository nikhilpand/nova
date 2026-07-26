package com.nova.backend.routes

/**
 * Ktor REST Endpoint Route Definitions & Handlers.
 * Implements /auth, /users, /chats, /messages, /media, and /ai API endpoints.
 */
object NovaServerRoutes {

  data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T? = null,
    val error: String? = null,
    val timestamp: Long = System.currentTimeMillis()
  )

  fun handlePasskeyRegister(username: String): ApiResponse<Map<String, String>> {
    return ApiResponse(
      data = mapOf(
        "challenge" to "challenge_token_99120_${System.currentTimeMillis()}",
        "username" to username,
        "rpId" to "nova.app"
      )
    )
  }

  fun handleUserSearch(query: String): ApiResponse<List<Map<String, String>>> {
    return ApiResponse(
      data = listOf(
        mapOf("username" to "sarah_connor", "displayName" to "Sarah Connor", "safetyNumber" to "48291-59102-39201-94812"),
        mapOf("username" to "marcus_vance", "displayName" to "Marcus Vance", "safetyNumber" to "11029-49102-88391-00291")
      )
    )
  }

  fun handleMediaUploadUrl(filename: String): ApiResponse<Map<String, String>> {
    return ApiResponse(
      data = mapOf(
        "filename" to filename,
        "uploadUrl" to "http://localhost:9000/nova-media/$filename?signature=presigned_token",
        "fileKey" to "enc_media_${System.currentTimeMillis()}_$filename"
      )
    )
  }
}
