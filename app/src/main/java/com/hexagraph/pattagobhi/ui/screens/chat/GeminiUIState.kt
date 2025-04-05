package com.hexagraph.pattagobhi.ui.screens.chat

/**
 * A sealed hierarchy describing the state of the text generation.
 */
sealed interface GeminiUiState {

    /**
     * Empty state when the screen is first shown
     */
    object Initial : GeminiUiState

    /**
     * Still loading
     */
    object Loading : GeminiUiState

    /**
     * Text has been generated
     */
    data class Success(val outputText: String) : GeminiUiState

    /**
     * There was an error generating text
     */
    data class Error(val errorMessage: String) : GeminiUiState
}