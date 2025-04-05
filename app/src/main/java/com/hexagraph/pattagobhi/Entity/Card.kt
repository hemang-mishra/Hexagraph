package com.hexagraph.pattagobhi.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val deckId: Int,
    val question: String,
    val answer: String,
    val review: String
)
