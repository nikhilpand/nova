package com.nova.network

import io.github.jan_tennert.supabase.SupabaseClient
import io.github.jan_tennert.supabase.createSupabaseClient
import io.github.jan_tennert.supabase.postgrest.Postgrest
import io.github.jan_tennert.supabase.realtime.Realtime
import io.github.jan_tennert.supabase.realtime.realtime
import io.github.jan_tennert.supabase.storage.Storage
import io.github.jan_tennert.supabase.gotrue.Auth
import io.github.jan_tennert.supabase.functions.Functions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class SupabaseRealtimeEvent(
    val channel: String,
    val eventType: String,
    val payloadJson: String
)

/**
 * Production Supabase Cloud Infrastructure Manager.
 * Wraps io.github.jan-tennert.supabase Kotlin SDK.
 * Configures PostgreSQL (Postgrest), WebSockets (Realtime), Auth, Storage, and Edge Functions.
 */
class SupabaseClientManager {

    companion object {
        const val DEFAULT_URL = "https://oqqqzdhxwpqsholcfdsg.supabase.co"
        const val DEFAULT_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9xcXF6ZGh4d3Bxc2hvbGNmZHNnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODUwNzMwNzAsImV4cCI6MjEwMDY0OTA3MH0.1H8TGa8bEAjnc9YorI_jUTuCf0o8KSIci-Yx8ph6J1A"
    }

    var client: SupabaseClient? = null
        private set

    private val _realtimeEvents = MutableSharedFlow<SupabaseRealtimeEvent>()
    val realtimeEvents: SharedFlow<SupabaseRealtimeEvent> = _realtimeEvents.asSharedFlow()

    fun initializeSupabase(url: String = DEFAULT_URL, anonKey: String = DEFAULT_ANON_KEY) {
        client = createSupabaseClient(
            supabaseUrl = url,
            supabaseKey = anonKey
        ) {
            install(Postgrest)
            install(Realtime)
            install(Auth)
            install(Storage)
            install(Functions)
        }
        println("⚡ Supabase Client initialized ($url)")
    }

    suspend fun subscribeToRealtimeChatChannel(chatId: String) {
        val currentClient = client ?: return
        try {
            val channel = currentClient.realtime.channel("chat_$chatId")
            channel.subscribe()
            println("📡 Subscribed to Supabase Realtime channel: chat_$chatId")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getStorageUploadUrl(bucket: String, filename: String): String {
        return "$DEFAULT_URL/storage/v1/object/public/$bucket/$filename"
    }
}
