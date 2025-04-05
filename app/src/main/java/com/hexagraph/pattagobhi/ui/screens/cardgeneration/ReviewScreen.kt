package com.hexagraph.pattagobhi.ui.screens.cardgeneration


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hexagraph.pattagobhi.Entity.Card

@Composable
fun ReviewScreen(uiState: CardGenerationUIState) {
    var currentIndex by remember { mutableStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }

    val allCards = uiState.easyCards + uiState.mediumCards + uiState.hardCards

    if (allCards.isNotEmpty()) {
        val currentCard = allCards[currentIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Question: ${currentCard.question}", modifier = Modifier.padding(8.dp))
            if (showAnswer) {
                Text(text = "Answer: ${currentCard.answer}", modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            if(!showAnswer)
            Button(onClick = { showAnswer = !showAnswer }) {
                Text(text = if (showAnswer) "Next" else "Show Answer")
            }
            if (showAnswer && currentIndex < allCards.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    currentIndex++
                    showAnswer = false
                }) {
                    Text(text = "Next Question")
                }
            }
        }
    } else {
        Text(text = "No cards available", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ReviewScreenBase(){

}