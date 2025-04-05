package com.hexagraph.pattagobhi.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Deck(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = ""
)
