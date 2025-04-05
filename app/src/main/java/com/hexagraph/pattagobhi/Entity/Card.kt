package com.hexagraph.pattagobhi.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hexagraph.pattagobhi.util.Review

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val deckId: Int = 0,
    val question: String = "",
    val answer: String = "",
    val review: Review = Review.EASY,
    val reviewRecord: List<String> = emptyList(),
    val nextReview: String = ""
)
