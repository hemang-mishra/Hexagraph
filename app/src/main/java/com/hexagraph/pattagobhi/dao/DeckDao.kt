package com.hexagraph.pattagobhi.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.ui.screens.deck.ReviewCount
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesk(desk: Deck)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<Card>)

    @Query("SELECT * FROM deck")
    fun getAllDeck(): Flow<List<Deck>>
    @Query("SELECT * FROM card WHERE deckId = :deckId")
    fun getCardByDeck(deckId:Int):Flow<List<Card>>



    @Update
    suspend fun updateCard(card:Card)

    @Delete
    suspend fun deleteCard(card:Card)

    @Delete
    suspend fun deleteDeck(deck: Deck)

    @Query("""
    SELECT deckId, review, COUNT(*)  as count, nextReview
    FROM Card 
    GROUP BY deckId, review
""")
    fun getReviewCountsByDeck(): Flow<List<ReviewCount>>

    @Query("SELECT * FROM Card")
    fun getAllCards(): Flow<List<Card>>

    @Query("DELETE FROM deck")
    suspend fun deleteAllDecks()

    @Query("DELETE FROM card")
    suspend fun deleteAllCards()
}