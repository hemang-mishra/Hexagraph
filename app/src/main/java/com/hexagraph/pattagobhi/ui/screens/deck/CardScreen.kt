package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexagraph.pattagobhi.util.Review

@Composable
fun CardScreen(deckId: Int, name: String, viewModel: CardScreenViewModel = hiltViewModel()) {

    Scaffold { innerPadding ->
        LaunchedEffect(Unit) {
            viewModel.getAllCard(deckId)
        }
        val state by viewModel.uiState.collectAsState()
        val cards = state.cards
        val size = cards.size
        val hard = cards.count { it.review == Review.HARD }
        val medium = cards.count { it.review == Review.MEDIUM }
        val easy = cards.count { it.review == Review.EASY }


        val starterScreen = remember { mutableStateOf(true) }
        if (starterScreen.value)
            Box {
                DeckStatsScreen(name, easy, medium, hard, 0, size)
                Button(
                    onClick = {
                        starterScreen.value = false
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .width(150.dp)
                ) {
                    Text("Study")
                }
            }else{
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    state.cards.forEach { card ->
                        Text(card.question)
                    }
                }
            }
        }
    }
}


@Composable
fun DeckStatsScreen(
    title: String,
    newCount: Int,
    learningCount: Int,
    reviewCount: Int,
    totalNew: Int,
    totalCards: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        StatsRow("New", newCount, Color.Blue)
        StatsRow("Learning", learningCount, Color.Red)
        StatsRow("To Review", reviewCount, Color.Green)

        Spacer(modifier = Modifier.height(16.dp))

        StatsRow("Total new cards", totalNew, Color.White)
        StatsRow("Total cards", totalCards, Color.White)
    }
}

@Composable
fun StatsRow(label: String, value: Int, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White, fontSize = 16.sp)
        Text(text = value.toString(), color = valueColor, fontSize = 16.sp)
    }
}