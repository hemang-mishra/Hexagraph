package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckScreen(
    viewModel: DeckScreenViewModel = hiltViewModel(),
    onDeckClicked: (Int) -> Unit,
    onButtonClicked: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getAllDeck()
    }
    val state by viewModel.uiState.collectAsState()
    val decks = state.decks
//
//    LazyColumn {
//        item {
//            state.decks.forEach { deck ->
//                Text(deck.name, modifier = Modifier.clickable {
//                    onDeckClicked(deck.id)
//                })
//            }
//        }
//    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MindStacks") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open Drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* TODO: Refresh */ }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    IconButton(onClick = { /* TODO: More */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onButtonClicked() }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            item {
                decks.forEach { deck ->
                    DeckCard(deck = deck, onClick = {
                        onDeckClicked(deck.id)
                    })
                }
            }
        }
    }

}

@Composable
fun DeckCard(deck: Deck, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(deck.name, color = Color.White)

//            Row {
//                Text(deck.total.toString(), color = Color.Blue, modifier = Modifier.padding(end = 8.dp))
//                Text(deck.review.toString(), color = Color.Red, modifier = Modifier.padding(end = 8.dp))
//                Text(deck.newCount.toString(), color = Color.Green)
//            }
        }
    }
}