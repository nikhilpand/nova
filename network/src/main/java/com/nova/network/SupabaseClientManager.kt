package com.nova.network

import io.github.jan_tennert.supabase.SupabaseClient
import io.github.jan_tennert.supabase.createSupabaseClient
import io.github.jan_tennert.supabase.postgrest.Postgrest
import io.github.jan_tennert.supabase.realtime.Realtime
import io.github.jan_tennert.supabase.realtime.realtime
import io.github.jan_tennert.supabase.storage.Storage
import io.github.jan_tennert.supabase.storage.storage
import io.github.jan_tennert.supabase.auth.Auth
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

    var client: SupabaseClient? = null
        private set

    private val _realtimeEvents = MutableSharedFlow<SupabaseRealtimeEvent>()
    val realtimeEvents: SharedFlow<SupabaseRealtimeEvent> = _realtimeEvents.asSharedFlow()

    fun initializeSupabase(url: String, anonKey: String) {
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
        val channel = currentClient.realtime.channel("chat_$chatId")
        channel.subscribe()
        println("📡 Subscribed to Supabase Realtime channel: chat_$chatId")
    }

    fun getStorageUploadUrl(bucket: String, filename: String): String {
        val currentClient = client ?: return ""
        return currentClient.storage.from(bucket).publicUrl(filename)
    }
}
