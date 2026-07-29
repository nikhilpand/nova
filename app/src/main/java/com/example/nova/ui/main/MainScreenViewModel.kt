package com.example.nova.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova.data.Conversation
import com.example.nova.data.NovaRepository
import com.example.nova.ui.main.MainScreenUiState.Success
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
  private val novaRepository: NovaRepository = NovaRepository()
) : ViewModel() {

  val uiState: StateFlow<MainScreenUiState> =
    novaRepository.conversations
      .map<List<Conversation>, MainScreenUiState>(::Success)
      .catch { emit(MainScreenUiState.Error(it)) }
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUiState.Loading)
}

sealed interface MainScreenUiState {
  object Loading : MainScreenUiState

  data class Error(val throwable: Throwable) : MainScreenUiState

  data class Success(val data: List<Conversation>) : MainScreenUiState
}
