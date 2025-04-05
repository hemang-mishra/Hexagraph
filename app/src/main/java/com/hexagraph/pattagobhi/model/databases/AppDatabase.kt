package com.hexagraph.pattagobhi.model.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.dao.DeckDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Deck::class, Card::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun deskDao(): DeckDao

    class Callback(
        private val scope: CoroutineScope,
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // Insert default data
            val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "flashcard-db"
            ).build()

            val dao = database.deskDao()

            scope.launch {
                val defaultDeck = Deck(id = 1, name = "Default Deck")
                val defaultCard = Card(
                    id = 1,
                    deckId = 1,
                    question = "What is Kotlin?",
                    answer = "A modern programming language for Android",
                    review = "Easy"
                )
                dao.insertDesk(defaultDeck)
                dao.insertCard(defaultCard)
            }
        }
    }
}