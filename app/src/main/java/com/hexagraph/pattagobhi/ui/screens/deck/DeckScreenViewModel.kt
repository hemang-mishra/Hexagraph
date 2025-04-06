package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.repository.DeckRepository
import com.hexagraph.pattagobhi.util.Review
import com.hexagraph.pattagobhi.util.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
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

    //    fun getAllDeckUI() {
//        viewModelScope.launch {
//            repository.deckUIFlow.collect { deckUIList ->
//                _uiState.value = _uiState.value.copy(decks = deckUIList)
//            }
//        }
//    }
//    fun getAllDeckUI() {
//        viewModelScope.launch {
//            val list: MutableList<DeckUI> = emptyList<DeckUI>().toMutableList()
//            val decks = repository.getAllDeck().first()
//            decks.forEach { deck ->
//                val cards = repository.getCardByDeckId(deck.id).first()
//                val size = cards.size
//                val currentTime = getCurrentTime()
//                val hard = cards.count { it.review == Review.HARD && currentTime >= it.nextReview }
//                val medium =
//                    cards.count { it.review == Review.MEDIUM && currentTime >= it.nextReview }
//                val easy = cards.count { it.review == Review.EASY && currentTime >= it.nextReview }
//                list.add(
//                    DeckUI(
//                        id = deck.id,
//                        name = deck.name,
//                        hardCount = hard,
//                        mediumCount = medium,
//                        easyCount = easy,
//                        totalCount = size
//                    )
//                )
//            }
//            _uiState.value = _uiState.value.copy(decks = list)
//        }
//    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllDeckUI() {
        viewModelScope.launch {
            // Listen continuously to the flow of decks
            repository.getAllDeck()
                .flatMapLatest { decks ->
                    if (decks.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        // For each deck, get its cards as a flow, then combine all resulting flows
                        combine(decks.map { deck ->
                            repository.getCardByDeckId(deck.id).map { cards ->
                                val currentTime = getCurrentTime()
                                val hard = cards.count { it.review == Review.HARD && currentTime >= it.nextReview }
                                val medium = cards.count { it.review == Review.MEDIUM && currentTime >= it.nextReview }
                                val easy = cards.count { it.review == Review.EASY && currentTime >= it.nextReview }
                                DeckUI(
                                    id = deck.id,
                                    name = deck.name,
                                    hardCount = hard,
                                    mediumCount = medium,
                                    easyCount = easy,
                                    totalCount = cards.size
                                )
                            }
                        }) { deckUIs -> deckUIs.toList() }
                    }
                }
                .collect { deckUIList ->
                    // Update UI state with the latest list of DeckUI items
                    _uiState.value = _uiState.value.copy(decks = deckUIList)
                }
        }
    }

}

data class DeckUI(
    val id: Int,
    val name: String,
    val hardCount: Int,
    val mediumCount: Int,
    val easyCount: Int,
    val totalCount: Int
)

data class ReviewCount(
    val deckId: Int,
    val review: Review,
    val count: Int,
    val nextReview: String,
)