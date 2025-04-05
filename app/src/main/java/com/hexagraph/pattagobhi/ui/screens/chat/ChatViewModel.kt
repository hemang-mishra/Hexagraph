package com.hexagraph.pattagobhi.ui.screens.chat

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.content
import com.hexagraph.pattagobhi.service.GeminiService
import com.hexagraph.pattagobhi.ui.screens.onboarding.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : BaseViewModel<ChatUIState>() {
    private val history = MutableStateFlow<List<BotComponent>>(emptyList())
    private val chatUIStateFlow = MutableStateFlow<ChatUIState>(ChatUIState())
    private val geminiService: GeminiService = GeminiService()
    fun sendPrompt(
        prompt: String,
        basePrompts: String = GeminiPrompts.defaultPrompt()
    ) {
//        data.value = GeminiData()
//        _uiState.value = GeminiUiState.Loading
        addHistory(BotComponent.UserResponseState("1", prompt))
//        chatUiState.value = BotUiState.GEMINI
        viewModelScope.launch(Dispatchers.IO) {
            chatUIStateFlow.emit(chatUIStateFlow.value.copy(isLoading = true,
                currentInteraction = BotUiState.GEMINI))
            try {
                val response = geminiService.generateContent(prompt = basePrompts+prompt)
                response?.let { outputContent ->
                    addHistory(
                        BotComponent.GeminiResponseState(
                            System.currentTimeMillis().toString(),
                            outputContent.replace("*", "")
                        )
                    )
//                    _uiState.value = GeminiUiState.Success("")
//                    chatUiState.value = BotUiState.MENU
                }
            } catch (e: Exception) {
                chatUIStateFlow.emit(chatUIStateFlow.value.copy(isLoading = true, geminiUiState = GeminiUiState.Error(e.message.toString())))
            }
        }
    }

    fun addHistory(comp: BotComponent) {
        viewModelScope.launch {
            history.emit(
                history.value + comp,
            )
            changeScrollState(false)
        }
    }

    fun onClick(snackbarHostState: SnackbarHostState, prompt: String){
        sendPrompt(prompt)
    }

    fun changeScrollState(isScrollEnabled: Boolean) {
        viewModelScope.launch {
            chatUIStateFlow.emit(
                chatUIStateFlow.value.copy(
                    isScrollRequired = isScrollEnabled
                )
            )
        }
    }

    override val uiState: StateFlow<ChatUIState> = createUiStateFlow()
    override fun createUiStateFlow(): StateFlow<ChatUIState> {
        return combine(chatUIStateFlow, history){
            chatUIState, history ->
            chatUIState.copy(
                history = history
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            ChatUIState()
        )
    }
}