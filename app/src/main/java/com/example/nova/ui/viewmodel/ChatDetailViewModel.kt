package com.example.nova.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova.data.Conversation
import com.example.nova.data.Message
import com.example.nova.data.MessageType
import com.example.nova.security.CryptoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

import com.example.nova.data.NovaRepository

data class ChatDetailUiState(
    val conversation: Conversation? = null,
    val messages: List<Message> = emptyList(),
    val isE2eeActive: Boolean = true,
    val isTyping: Boolean = false
)

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val cryptoManager: CryptoManager,
    private val novaRepository: NovaRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(ChatDetailUiState())
    val uiState: StateFlow<ChatDetailUiState> = _uiState.asStateFlow()

    fun loadChat(chatId: String) {
        viewModelScope.launch {
            // Load chat and messages from Room SQLCipher DB
        }
    }

    fun sendMessage(content: String, type: MessageType = MessageType.TEXT) {
        viewModelScope.launch {
            val encryptedPayload = cryptoManager.encryptMessage(content)
            val newMessage = Message(
                id = UUID.randomUUID().toString(),
                conversationId = _uiState.value.conversation?.id ?: "",
                senderId = "user_me",
                senderName = "Me",
                content = content,
                type = type,
                timestamp = System.currentTimeMillis(),
                isE2ee = encryptedPayload.isE2eeVerified
            )
            _uiState.value = _uiState.value.copy(
                messages = _uiState.value.messages + newMessage
            )
        }
    }
}
