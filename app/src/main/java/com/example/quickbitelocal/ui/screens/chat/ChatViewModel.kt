package com.example.quickbitelocal.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

data class ChatUiState(
    val messages: List<ChatMessage> = listOf(
        ChatMessage("Hello! I'm your QuickBite assistant. How can I help you today?", false)
    ),
    val isLoading: Boolean = false
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "user") { text("You are a helpful customer support assistant for QuickBite Local, a hyperlocal food delivery app. Answer queries about orders, refunds, and restaurant recommendations concisely.") },
            content(role = "model") { text("Understood. I'm ready to help QuickBite Local customers.") }
        )
    )

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return

        val userMessage = ChatMessage(userText, true)
        _uiState.update { it.copy(
            messages = it.messages + userMessage,
            isLoading = true
        ) }

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(userText)
                val botMessage = ChatMessage(response.text ?: "I'm sorry, I couldn't process that.", false)
                _uiState.update { it.copy(
                    messages = it.messages + botMessage,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    messages = it.messages + ChatMessage("Error: ${e.localizedMessage}", false),
                    isLoading = false
                ) }
            }
        }
    }
}
