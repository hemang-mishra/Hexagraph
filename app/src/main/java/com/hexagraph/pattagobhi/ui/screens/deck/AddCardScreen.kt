package com.hexagraph.pattagobhi.ui.screens.deck

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.util.getCurrentTime
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun AddCardScreen(
    deckId: Int,
    cardScreenViewModel: CardScreenViewModel = hiltViewModel(),
    onCardAdded:()->Unit
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var markDownPreview by remember { mutableStateOf(false) }
    Log.d("MarkDown", answer)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Question Label
        Text("Question", color = Color.White)

        // Question TextField
        if (markDownPreview)
            MarkdownText(
                question.trimIndent(),
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(color = Color.White)
            )
        else
            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF1E1E1E),
                    unfocusedContainerColor = Color(0xFF1E1E1E),
                ),
                shape = RoundedCornerShape(12.dp)

            )

        Divider(color = Color.Gray, thickness = 1.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Answer", color = Color.White, modifier = Modifier.weight(1f))
            IconButton(onClick = { markDownPreview = !markDownPreview }) {
                if (markDownPreview)
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                else Icon(Icons.Default.FolderOpen, contentDescription = null, tint = Color.White)
            }
        }

        // Answer TextField (multi-line)
        if (markDownPreview)
            MarkdownText(
                answer.trimIndent(),
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(color = Color.White)
            )
        else
            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF1E1E1E),
                    unfocusedContainerColor = Color(0xFF1E1E1E)
                ),
                maxLines = 10,
                shape = RoundedCornerShape(12.dp)
            )

        Spacer(modifier = Modifier.weight(1f))

        // Add to Deck Button
        Button(
            onClick = {
                cardScreenViewModel.addCard(
                    Card(
                        deckId = deckId,
                        question = question,
                        answer = answer,
                        nextReview = getCurrentTime()
                    )
                )
                onCardAdded()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3C4F35), // olive greenish
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(50)
        ) {
            Text("Add to Deck")
        }
    }
}
