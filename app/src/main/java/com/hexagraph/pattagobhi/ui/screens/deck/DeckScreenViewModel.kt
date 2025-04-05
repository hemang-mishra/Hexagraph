package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.repository.DeckRepository
import com.hexagraph.pattagobhi.util.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DeckScreenUIState(
    val decks: List<DeckUI> = emptyList()
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
        getAllDeckUI()
    }

    fun getAllDeckUI() {
        viewModelScope.launch {
            repository.deckUIFlow.collect { deckUIList ->
                _uiState.value = _uiState.value.copy(decks = deckUIList)
            }
        }
    }

}

data class DeckUI(
    val id: Int,
    val name: String,
    val hardCount : Int,
    val mediumCount : Int,
    val easyCount : Int,
    val totalCount : Int
)
data class ReviewCount(
    val deckId: Int,
    val review: Review,
    val count: Int,
    val nextReview: String,
)