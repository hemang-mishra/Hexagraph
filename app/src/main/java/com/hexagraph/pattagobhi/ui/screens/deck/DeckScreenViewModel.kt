package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.repository.DeckRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DeckScreenUIState(
    val decks: List<Deck> = emptyList()
)

@HiltViewModel
class DeckScreenViewModel @Inject constructor(
    private val repository: DeckRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(DeckScreenUIState())
    val uiState: StateFlow<DeckScreenUIState> = _uiState
    fun addDesk(deck: Deck) {
        viewModelScope.launch {
            repository.insertDesk(deck)
        }
    }

    init {
        getAllDeck()
    }

    fun getAllDeck() {
        viewModelScope.launch {
            repository.getAllDeck().collect { decks ->
                _uiState.value = _uiState.value.copy(decks = decks)
            }
        }
    }


}