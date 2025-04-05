package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.repository.DeckRepository
import com.hexagraph.pattagobhi.util.Review
import com.hexagraph.pattagobhi.util.getCurrentTime
import com.hexagraph.pattagobhi.util.getNextReviewTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CardScreenUIState(
    val cards: List<Card> = emptyList()
)

@HiltViewModel
class CardScreenViewModel @Inject constructor(
    private val repository: DeckRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardScreenUIState())
    val uiState: StateFlow<CardScreenUIState> = _uiState


    fun getAllCard(deckId: Int) {
        viewModelScope.launch {
            repository.getCardByDeckId(deckId).collect { cards ->
                _uiState.value = _uiState.value.copy(cards = cards)
            }
        }
    }

    fun addCard(card: Card) {
        val cardRecords: MutableList<String> = card.reviewRecord.toMutableList()
        var nextReview = ""
        if (card.review == Review.EASY) nextReview = getNextReviewTime(1, 5.5)
        if (card.review == Review.MEDIUM) nextReview = getNextReviewTime(1, 4.5)
        if (card.review == Review.HARD) nextReview = getNextReviewTime(1, 2.5)
        cardRecords.add(getCurrentTime())
        viewModelScope.launch {
            repository.insertCard(card.copy(reviewRecord = cardRecords, nextReview = nextReview))
        }
    }

    fun updateCard(card: Card) {
        val cardRecords: MutableList<String> = card.reviewRecord.toMutableList()
        var nextReview = ""
        if (card.review == Review.EASY) nextReview = getNextReviewTime(1, 5.5)
        if (card.review == Review.MEDIUM) nextReview = getNextReviewTime(1, 4.5)
        if (card.review == Review.HARD) nextReview = getNextReviewTime(1, 2.5)
        cardRecords.add(getCurrentTime())
        viewModelScope.launch {
            repository.updateCard(card.copy(reviewRecord = cardRecords, nextReview = nextReview))
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch {
            repository.deleteCard(card)
        }
    }

}