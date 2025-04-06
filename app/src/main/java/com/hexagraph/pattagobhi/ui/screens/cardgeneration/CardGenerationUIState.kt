package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.ui.screens.chat.GeminiPrompts

data class CardGenerationUIState(
    val easyQuestions: List<String> = emptyList(),
    val mediumQuestions: List<String> = emptyList(),
    val hardQuestions: List<String> = emptyList(),
    val easyCards: List<Card> = emptyList(),
    val mediumCards: List<Card> = emptyList(),
    val hardCards: List<Card> = emptyList(),
    val errorMessage: String? = null,
    val msgFlow: String? = null,
    val cardGenerationUIStateForUI: CardGenerationUIStateForUI = CardGenerationUIStateForUI(),
    val currentScreen: CurrentScreen = CurrentScreen.TopicInputScreen,
    val previousScreen: CurrentScreen? = null,
    val reviewScreenUIState: ReviewScreenUIState = ReviewScreenUIState(),
){
    val prompt: String
        get() {
            val index = reviewScreenUIState.currentIndex
            val currentCard = easyCards.getOrNull(index)
                ?: mediumCards.getOrNull(index+ easyCards.size)
                ?: hardCards.getOrNull(index+ easyCards.size + mediumCards.size)
            if(currentCard != null)
            return GeminiPrompts.generateHelpPrompt(answer = currentCard.answer, question = currentCard.question)
            else return ""
        }
}

data class CardGenerationUIStateForUI(
    val topic: String = "Android Lifecycle",
    val easyQuestions: String = "4",
    val mediumQuestions: String = "3",
    val hardQuestions: String = "2",
    val errorMessage: String? = null,
    val deck: Deck = Deck()
) {
    val isEasyQuestionsValid: Boolean
        get() = easyQuestions.toIntOrNull() != null

    val isMediumQuestionsValid: Boolean
        get() = mediumQuestions.toIntOrNull() != null

    val isHardQuestionsValid: Boolean
        get() = hardQuestions.toIntOrNull() != null
}

data class ReviewScreenUIState(
    val currentIndex: Int = 0,
    val currentState : CurrentStateOfReviewScreen = CurrentStateOfReviewScreen.OnlyQuestionDisplayed,
    val feedbackText: String? = null,
    val voiceText: String? = null,
    val isTextToSpeechActive: Boolean = false
)

enum class CurrentScreen {
    TopicInputScreen,
    ReviewScreen,
    ChatScreen,
    Loading
}

enum class CurrentStateOfReviewScreen{
    OnlyQuestionDisplayed,
    RecordingInProgress,
    FeedbackInProgress,
    AnswerIsDisplayed,
    AnswerIsDisplayedWithFeedback,
}