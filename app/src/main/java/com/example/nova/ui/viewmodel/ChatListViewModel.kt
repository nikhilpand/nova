package com.example.nova.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova.data.ChatCategory
import com.example.nova.data.Conversation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.nova.data.NovaRepository

data class ChatListUiState(
    val conversations: List<Conversation> = emptyList(),
    val selectedCategory: ChatCategory = ChatCategory.ALL,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val novaRepository: NovaRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    init {
        loadConversations()
    }

    fun loadConversations() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Load conversations from local encrypted DB or remote sync
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun selectCategory(category: ChatCategory) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}
