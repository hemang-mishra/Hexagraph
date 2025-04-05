package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DeckScreen(viewModel: DeckScreenViewModel = hiltViewModel(), onDeckClicked: (Int) -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.getAllDeck()
    }
    val state by viewModel.uiState.collectAsState()

    LazyColumn {
        item {
            state.decks.forEach { deck ->
                Text(deck.name, modifier = Modifier.clickable {
                    onDeckClicked(deck.id)
                })
            }
        }
    }
}