package com.hexagraph.pattagobhi.repository

import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.dao.DeckDao
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
}