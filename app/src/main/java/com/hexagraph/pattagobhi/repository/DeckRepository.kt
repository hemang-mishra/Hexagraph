package com.hexagraph.pattagobhi.repository

import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.dao.DeckDao
import com.hexagraph.pattagobhi.ui.screens.deck.DeckUI
import com.hexagraph.pattagobhi.util.Review
import com.hexagraph.pattagobhi.util.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val deckDao: DeckDao
) {
    suspend fun insertDesk(deck: Deck) = deckDao.insertDesk(deck)
    suspend fun insertCard(card: Card) = deckDao.insertCard(card)

    fun getAllDeck() = deckDao.getAllDeck()

    fun getCardByDeckId(deckId: Int) = deckDao.getCardByDeck(deckId)

    suspend fun updateCard(card: Card) = deckDao.updateCard(card)

    suspend fun deleteCard(card: Card) = deckDao.deleteCard(card)

    suspend fun deleteDeck(deck: Deck) = deckDao.deleteDeck(deck)

    val deckUIFlow: Flow<List<DeckUI>> = combine(
        deckDao.getAllDeck(),
        deckDao.getReviewCountsByDeck()
    ) { decks, counts ->

        val countMap = counts.groupBy { it.deckId }
        val currentTime = getCurrentTime()
        decks.map { deck ->
            val reviewCounts = countMap[deck.id].orEmpty()

            val hard = reviewCounts.find { it.review == Review.HARD && it.nextReview<=currentTime }?.count ?: 0
            val medium = reviewCounts.find { it.review == Review.MEDIUM  && it.nextReview<=currentTime}?.count ?: 0
            val easy = reviewCounts.find { it.review == Review.EASY  && it.nextReview<=currentTime}?.count ?: 0

            DeckUI(
                id = deck.id,
                name = deck.name,
                hardCount = hard,
                mediumCount = medium,
                easyCount = easy,
                totalCount = counts.size
            )
        }
    }





}