package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.repository.DeckRepository
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


}