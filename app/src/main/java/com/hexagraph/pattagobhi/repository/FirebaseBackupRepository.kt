package com.hexagraph.pattagobhi.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.tasks.await
import java.util.Locale

@Singleton
class FirebaseBackupRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    private val currentUser = auth.currentUser
    private val sanitizedEmail: String = currentUser?.email
        ?.lowercase(Locale.getDefault())
        ?.replace(".", ",")
        ?: throw IllegalStateException("User not authenticated")

    /**
     * Backup a Deck object to Firestore under the authenticated user's decks subcollection.
     */
    fun backupDeck(deck: Deck, onComplete: (Boolean, String?) -> Unit) {
        val decksCollection = db.collection("users")
            .document(sanitizedEmail)
            .collection("decks")

        // Use deck.id if available; otherwise, let Firestore generate one.
        val documentId = if (deck.id != 0) deck.id.toString() else decksCollection.document().id

        decksCollection.document(documentId)
            .set(deck, SetOptions.merge())
            .addOnSuccessListener { onComplete(true, null) }
            .addOnFailureListener { e ->
                Log.e("BackupDeck", "Error backing up deck", e)
                onComplete(false, e.message)
            }
    }

    /**
     * Backup a Card object to Firestore under the authenticated user's cards subcollection.
     */
    fun backupCard(card: Card, onComplete: (Boolean, String?) -> Unit) {
        val cardsCollection = db.collection("users")
            .document(sanitizedEmail)
            .collection("cards")

        // Use card.id if available; otherwise, let Firestore generate one.
        val documentId = if (card.id != 0) card.id.toString() else cardsCollection.document().id

        cardsCollection.document(documentId)
            .set(card, SetOptions.merge())
            .addOnSuccessListener { onComplete(true, null) }
            .addOnFailureListener { e ->
                Log.e("BackupCard", "Error backing up card", e)
                onComplete(false, e.message)
            }
    }

    suspend fun fetchDecks(): List<Deck> {
        return try {
            val snapshot = db.collection("users").document(sanitizedEmail).collection("decks").get().await()
            snapshot.toObjects(Deck::class.java)
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "$e")
            emptyList()
        }
    }

    suspend fun fetchCards(): List<Card> {
        return try {
            val snapshot = db.collection("users").document(sanitizedEmail).collection("cards").get().await()
            snapshot.toObjects(Card::class.java)
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "$e")
            emptyList()
        }
    }
}