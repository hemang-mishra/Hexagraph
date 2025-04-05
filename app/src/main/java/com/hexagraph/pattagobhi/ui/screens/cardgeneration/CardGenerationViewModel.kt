package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.model.ResponseError
import com.hexagraph.pattagobhi.service.GeminiService
import com.hexagraph.pattagobhi.ui.screens.chat.GeminiPrompts
import com.hexagraph.pattagobhi.ui.screens.onboarding.BaseViewModel
import com.hexagraph.pattagobhi.util.Review
import com.hexagraph.pattagobhi.util.Utils.separateQuestions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardGenerationViewModel @Inject constructor() :
    BaseViewModel<CardGenerationUIState>() {

    private val createGenerationUIStateFlow = MutableStateFlow(CardGenerationUIState())
    private val uiStateForUIFlow = MutableStateFlow(CardGenerationUIStateForUI())
    private val geminiService = GeminiService()

    fun generateQuestions(topic: String, easyCount: Int, mediumCount: Int, hardCount: Int) {
        viewModelScope.launch {
            createGenerationUIStateFlow.value =
                createGenerationUIStateFlow.value.copy(currentScreen = CurrentScreen.Loading)
            try {
                val prompt = GeminiPrompts.generateQuestionsPrompt(
                    topic,
                    easyCount,
                    mediumCount,
                    hardCount
                )
                val response = geminiService.generateContent(prompt = prompt)

                if (response != null) {
                    val (easyQuestions, mediumQuestions, hardQuestions) = separateQuestions(
                        response,
                        easyCount,
                        mediumCount,
                        hardCount
                    )
                    createGenerationUIStateFlow.value = createGenerationUIStateFlow.value.copy(
//                        currentScreen = CurrentScreen.ReviewScreen,
                        easyQuestions = easyQuestions,
                        mediumQuestions = mediumQuestions,
                        hardQuestions = hardQuestions,
                        errorMessage = null
                    )
                    fetchAnswers()
                } else {
                    emitError(ResponseError.UNKNOWN)
                }
            } catch (e: Exception) {
                emitError(ResponseError.NETWORK_ERROR)
            }
        }
    }

    fun fetchAnswers() {
        viewModelScope.launch {
//            createGenerationUIStateFlow.value =
//                createGenerationUIStateFlow.value.copy(currentScreen = CurrentScreen.Loading)
            try {
                val easyQuestions = createGenerationUIStateFlow.value.easyQuestions
                val mediumQuestions = createGenerationUIStateFlow.value.mediumQuestions
                val hardQuestions = createGenerationUIStateFlow.value.hardQuestions

                val easyCards = async {
                    easyQuestions.map { question ->
                        val prompt = GeminiPrompts.generateAnswerPrompt(
                            topic = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.topic,
                            question = question,
                            difficulty = "Easy"
                        )
                        val response = geminiService.generateContent(prompt = prompt)
                        if (response != null) {
                            Card(
                                deckId = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.deckId,
                                question = question,
                                answer = response,
                            )
                        } else {
                            null
                        }
                    }.filterNotNull()
                }

                val mediumCards = async {
                    mediumQuestions.map { question ->
                        val prompt = GeminiPrompts.generateAnswerPrompt(
                            topic = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.topic,
                            question = question,
                            difficulty = "Medium"
                        )
                        val response = geminiService.generateContent(prompt = prompt)
                        if (response != null) {
                            Card(
                                deckId = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.deckId,
                                question = question,
                                answer = response,
                            )
                        } else {
                            null
                        }
                    }.filterNotNull()
                }

                val hardCards = async {
                    hardQuestions.map { question ->
                        val prompt = GeminiPrompts.generateAnswerPrompt(
                            topic = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.topic,
                            question = question,
                            difficulty = "Hard"
                        )
                        val response = geminiService.generateContent(prompt = prompt)
                        if (response != null) {
                            Card(
                                deckId = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.deckId,
                                question = question,
                                answer = response,
                            )
                        } else {
                            null
                        }
                    }.filterNotNull()
                }

                createGenerationUIStateFlow.value = createGenerationUIStateFlow.value.copy(
                    currentScreen = CurrentScreen.ReviewScreen,
                    easyCards = easyCards.await(),
                    mediumCards = mediumCards.await(),
                    hardCards = hardCards.await(),
                    errorMessage = null
                )
                val hello = ""
            } catch (e: Exception) {
                emitError(ResponseError.NETWORK_ERROR)
            }
        }
    }

    fun updateUIStateForUI(
        topic: String? = null,
        easyQuestions: String? = null,
        mediumQuestions: String? = null,
        hardQuestions: String? = null
    ) {
        val currentState = uiStateForUIFlow.value
        uiStateForUIFlow.value = currentState.copy(
            topic = topic ?: currentState.topic,
            easyQuestions = easyQuestions ?: currentState.easyQuestions,
            mediumQuestions = mediumQuestions ?: currentState.mediumQuestions,
            hardQuestions = hardQuestions ?: currentState.hardQuestions
        )
    }

    fun switchScreen(destinationScreen: CurrentScreen) {
        val currentState = createGenerationUIStateFlow.value
        createGenerationUIStateFlow.value = currentState.copy(
            previousScreen = currentState.currentScreen,
            currentScreen = destinationScreen
        )
    }

    override val uiState: StateFlow<CardGenerationUIState> = createUiStateFlow()
    override fun createUiStateFlow(): StateFlow<CardGenerationUIState> {
        return combine(
            createGenerationUIStateFlow,
            uiStateForUIFlow,
            errorFlow,
            successMsgFlow
        ) { stateFlow, uiStateForUI, error, msg ->
            stateFlow.copy(
                cardGenerationUIStateForUI = uiStateForUI,
                errorMessage = error?.genericToast,
                msgFlow = msg
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = CardGenerationUIState()
        )
    }
}