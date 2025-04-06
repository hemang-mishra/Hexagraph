package com.hexagraph.pattagobhi.ui.screens.deck

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexagraph.pattagobhi.util.Review
import com.hexagraph.pattagobhi.util.getCurrentTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(
    deckId: Int,
    name: String,
    viewModel: CardScreenViewModel = hiltViewModel(),
    onAddCardClicked: (Int) -> Unit,
    onReviewClicked: (Int) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open Drawer */ }) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Rename */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Rename")
                    }
                    IconButton(onClick = { /* TODO: Delete */ }) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "Delete")
                    }
                }
            )
        },
    ) { innerPadding ->
        LaunchedEffect(Unit) {
            viewModel.getAllCard(deckId)
        }
        val state by viewModel.uiState.collectAsState()
        val cards = state.cards
        val size = cards.size
        val currentTime = getCurrentTime()
        val hard = cards.count { it.review == Review.HARD && currentTime >= it.nextReview }
        val medium = cards.count { it.review == Review.MEDIUM && currentTime >= it.nextReview }
        val easy = cards.count { it.review == Review.EASY && currentTime >= it.nextReview }

        Box(modifier = Modifier.padding(innerPadding)) {
            DeckStatsScreen(name, easy, medium, hard, easy + medium + hard, size)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Button(
                    onClick = {
                        onReviewClicked(deckId)
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.8f), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF285669),
                        contentColor = Color.White
                    )

                ) {
                    Text("Review")
                }
                Button(
                    onClick = {
                        onAddCardClicked(deckId)
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.8f), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF83611E),
                        contentColor = Color.White
                    )
                ) {
                    Text("Add New Cards")
                }
            }
        }

    }
}


@Composable
fun DeckStatsScreen(
    title: String,
    easyCount: Int,
    mediumCount: Int,
    hardCount: Int,
    totalNew: Int,
    totalCards: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1D1B))

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    text = "To Review",
//                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
//                    modifier = Modifier.padding(bottom = 4.dp)
//                )
                StatsRow("Easy", easyCount, Color(0xFFB3DDFF))
                StatsRow("Medium", mediumCount, Color(0xFFDE596C))
                StatsRow("Hard", hardCount, Color(0xFF8BCE5A))

                Spacer(modifier = Modifier.height(16.dp))

                StatsRow("Total review cards", totalNew, Color.White)
                StatsRow("Total cards in deck", totalCards, Color.White)
            }

        }
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