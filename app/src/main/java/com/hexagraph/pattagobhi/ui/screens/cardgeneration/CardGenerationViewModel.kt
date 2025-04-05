package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.model.ResponseError
import com.hexagraph.pattagobhi.service.GeminiService
import com.hexagraph.pattagobhi.ui.screens.chat.GeminiPrompts
import com.hexagraph.pattagobhi.ui.screens.onboarding.BaseViewModel
import com.hexagraph.pattagobhi.util.Utils.separateQuestions
import dagger.hilt.android.lifecycle.HiltViewModel
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
                        currentScreen = CurrentScreen.ReviewScreen,
                        easyQuestions = easyQuestions,
                        mediumQuestions = mediumQuestions,
                        hardQuestions = hardQuestions,
                        errorMessage = null
                    )
                } else {
                    emitError(ResponseError.UNKNOWN)
                }
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
        ) { stateFlow, uiStateForUI, error, msg->
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