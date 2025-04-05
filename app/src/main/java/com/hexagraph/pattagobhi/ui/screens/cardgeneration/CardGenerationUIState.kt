package com.hexagraph.pattagobhi.ui.screens.cardgeneration

data class CardGenerationUIState(
    val isLoading: Boolean = false,
    val easyQuestions: List<String> = emptyList(),
    val mediumQuestions: List<String> = emptyList(),
    val hardQuestions: List<String> = emptyList(),
    val errorMessage: String? = null,
    val msgFlow: String? = null,
    val cardGenerationUIStateForUI: CardGenerationUIStateForUI = CardGenerationUIStateForUI()
)

data class CardGenerationUIStateForUI(
    val topic: String = "",
    val easyQuestions: String = "",
    val mediumQuestions: String = "",
    val hardQuestions: String = "",
    val errorMessage: String? = null,
    val deckId: Int = 1
) {
    val isEasyQuestionsValid: Boolean
        get() = easyQuestions.toIntOrNull() != null

    val isMediumQuestionsValid: Boolean
        get() = mediumQuestions.toIntOrNull() != null

    val isHardQuestionsValid: Boolean
        get() = hardQuestions.toIntOrNull() != null
}
