package com.hexagraph.pattagobhi.ui.screens.chat

data class ChatUIState(
    val isLoading: Boolean = false,
    val history: List<BotComponent> = emptyList(),
    val isScrollRequired: Boolean = false,
    val geminiUiState: GeminiUiState = GeminiUiState.Initial,
    val currentInteraction: BotUiState = BotUiState.GEMINI
)
