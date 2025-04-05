package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CardScreen(deckId: Int, viewModel: CardScreenViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getAllCard(deckId)
    }
    val state by viewModel.uiState.collectAsState()

    LazyColumn {
        item {
            state.cards.forEach { card ->
                Text(card.question)
            }
        }
    }
}