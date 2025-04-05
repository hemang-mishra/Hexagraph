package com.hexagraph.pattagobhi.model.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.Entity.Converters
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.dao.DeckDao
import com.hexagraph.pattagobhi.util.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Deck::class, Card::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
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
            ).fallbackToDestructiveMigration()
                .build()

            val dao = database.deskDao()

            scope.launch {
                val defaultDeck = Deck(id = 1, name = "Default Deck")
                val defaultCard = Card(
                    id = 1,
                    deckId = 1,
                    question = "What is Kotlin?",
                    answer = "A modern programming language for Android",
                    review = Review.EASY
                )
                dao.insertDesk(defaultDeck)
                dao.insertCard(defaultCard)
            }
        }
    }
}