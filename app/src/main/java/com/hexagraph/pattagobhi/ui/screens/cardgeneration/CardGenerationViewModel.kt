package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.dao.DeckDao
import com.hexagraph.pattagobhi.model.ResponseError
import com.hexagraph.pattagobhi.repository.DeckRepository
import com.hexagraph.pattagobhi.service.GeminiService
import com.hexagraph.pattagobhi.ui.screens.chat.GeminiPrompts
import com.hexagraph.pattagobhi.ui.screens.onboarding.BaseViewModel
import com.hexagraph.pattagobhi.util.Review
import com.hexagraph.pattagobhi.util.Utils.separateQuestions
import com.hexagraph.pattagobhi.util.calculateHalfLife
import com.hexagraph.pattagobhi.util.formatTime
import com.hexagraph.pattagobhi.util.getCurrentTime
import com.hexagraph.pattagobhi.util.getNextReviewTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardGenerationViewModel @Inject constructor(
    private val deckDao: DeckDao,
    private val deckRepository: DeckRepository,
) :
    BaseViewModel<CardGenerationUIState>() {

    private val createGenerationUIStateFlow = MutableStateFlow(CardGenerationUIState())
    private val uiStateForUIFlow = MutableStateFlow(CardGenerationUIStateForUI())
    private val geminiService = GeminiService()
    val listOfDecks = deckRepository.getAllDeck()

    fun selectDeck(deckId: Deck) {
        viewModelScope.launch {
            uiStateForUIFlow.emit(
                uiStateForUIFlow.value.copy(
                    deck = deckId
                )
            )
        }
    }

    fun generateFeedback() {
        viewModelScope.launch(Dispatchers.IO) {
            val uiState = createGenerationUIStateFlow.value
            val it = createGenerationUIStateFlow.value.reviewScreenUIState.currentIndex
            val card = uiState.easyCards.getOrNull(it)
                ?: uiState.mediumCards.getOrNull(it - uiState.easyCards.size)
                ?: uiState.hardCards.getOrNull(it - uiState.easyCards.size - uiState.mediumCards.size)
            val prompt = GeminiPrompts.feedbackOnResponse(
                question = card?.question ?: "",
                answer = card?.answer ?: "", response = uiState.reviewScreenUIState.voiceText ?: ""
            )
            val response = geminiService.generateContent(prompt = prompt)
            if (response != null) {
                reviewScreenStateFlow.emit(
                    reviewScreenStateFlow.value.copy(feedbackText = response)
                )
            } else {
                emitError(ResponseError.UNKNOWN)
            }
        }
    }

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
                                deckId = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.deck.id,
                                question = question,
                                answer = response,
                                review = Review.EASY
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
                                deckId = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.deck.id,
                                question = question,
                                answer = response,
                                review = Review.MEDIUM
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
                                deckId = createGenerationUIStateFlow.value.cardGenerationUIStateForUI.deck.id,
                                question = question,
                                answer = response,
                                review = Review.HARD
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

    //Review Screen codes
    private val reviewScreenStateFlow = MutableStateFlow(ReviewScreenUIState())

    fun switchReviewScreenState(currentStateOfReviewScreen: CurrentStateOfReviewScreen) {
        viewModelScope.launch {
            reviewScreenStateFlow.value = reviewScreenStateFlow.value.copy(
                currentState = currentStateOfReviewScreen
            )
        }
    }

    fun goToAParticularCard(index: Int) {
        if (index > createGenerationUIStateFlow.value.easyCards.size + createGenerationUIStateFlow.value.mediumCards.size + createGenerationUIStateFlow.value.hardCards.size || index < 0) {
            return
        }
        viewModelScope.launch {
            reviewScreenStateFlow.value = reviewScreenStateFlow.value.copy(
                currentIndex = index,
                currentState = CurrentStateOfReviewScreen.OnlyQuestionDisplayed
            )
        }
    }

    fun toggleSpeakerState(isSpeakerOn: Boolean) {
        viewModelScope.launch {
            reviewScreenStateFlow.value = reviewScreenStateFlow.value.copy(
                isTextToSpeechActive = isSpeakerOn
            )
        }
    }

    //Initialization for review
    fun initializeReviewScreen(deckId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deckDao.getCardByDeck(deckId).collectLatest {
                val easyCards = it.filter { card -> card.review == Review.EASY }
                val mediumCards = it.filter { card -> card.review == Review.MEDIUM }
                val hardCards = it.filter { card -> card.review == Review.HARD }
                createGenerationUIStateFlow.emit(
                    createGenerationUIStateFlow.value.copy(
                        easyCards = easyCards,
                        easyQuestions = easyCards.map { it.question },
                        mediumCards = mediumCards,
                        mediumQuestions = mediumCards.map { it.question },
                        hardCards = hardCards,
                        hardQuestions = hardCards.map { it.question },
                        currentScreen = CurrentScreen.ReviewScreen
                    )
                )
                uiStateForUIFlow.emit(
                    uiStateForUIFlow.value.copy(deck = listOfDecks.first().find { it.id == deckId }
                        ?: Deck())
                )
            }
        }
    }

    override val uiState: StateFlow<CardGenerationUIState> = createUiStateFlow()
    override fun createUiStateFlow(): StateFlow<CardGenerationUIState> {
        return combine(
            createGenerationUIStateFlow,
            uiStateForUIFlow,
            errorFlow,
            successMsgFlow,
            reviewScreenStateFlow
        ) { stateFlow, uiStateForUI, error, msg, reviewFlow ->
            stateFlow.copy(
                cardGenerationUIStateForUI = uiStateForUI,
                errorMessage = error?.genericToast,
                msgFlow = msg,
                reviewScreenUIState = reviewFlow
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = CardGenerationUIState()
        )
    }

    fun nextReviewTime(numberOfPreviousReview: Int, review: Review): String {
        var difficulty: Double = 0.0 // 0.2 0.6 0.9
        if (review == Review.HARD) difficulty = 0.9
        if (review == Review.MEDIUM) difficulty = 0.6
        if (review == Review.EASY) difficulty = 0.2
        return formatTime(calculateHalfLife(numberOfPreviousReview + 1, difficulty))
    }

    fun addCard(idx: Int, card: Card, cardIdx: Int) {
        val cardRecords: MutableList<String> = card.reviewRecord.toMutableList()
        var nextReview = ""
        if (idx == 2) {
            nextReview = getNextReviewTime(card.reviewRecord.size, 0.2)
            card.review = Review.EASY
        }
        if (idx == 1) {
            nextReview = getNextReviewTime(card.reviewRecord.size, 0.6)
            card.review = Review.MEDIUM
        }
        if (idx == 0) {
            nextReview = getNextReviewTime(card.reviewRecord.size, 0.9)
            card.review = Review.HARD

        }
        cardRecords.add(getCurrentTime())
        viewModelScope.launch {
            deckRepository.insertCard(
                card.copy(
                    reviewRecord = cardRecords,
                    nextReview = nextReview
                )
            )
            goToAParticularCard(cardIdx + 1)
        }
    }
}