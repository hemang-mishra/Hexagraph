package com.hexagraph.pattagobhi.ui.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.hexagraph.pattagobhi.ui.components.ChatRow

object GeminiPrompts{
    fun defaultPrompt() = "You are a chat bot friend to ask about users emotions " +
            "Don't ask counter questions"+
            "You can give recommendations to users and take their doubts." +
            "But respond like a friendly human. This is the user's query: "

    fun generateQuestionsPrompt(topic: String, easy: Int, medium: Int, hard: Int): String {
        return "Topic: $topic\n" +
                "Generate questions of difficulty level\n" +
                "Easy:$easy\n" +
                "Medium:$medium\n" +
                "Hard:$hard\n" +
                "Surround the question with @{ at start and }@ at the end.\n" +
                "Only show the questions\n" +
                "No unnecessary text"
    }

}

@Composable
fun GeminiResponseBase(geminiViewModel: ChatViewModel){
    var result by rememberSaveable { mutableStateOf("Hello I am your assistant....") }
    val uiState by geminiViewModel.uiState.collectAsState()
    val context = LocalContext.current
    when(uiState.geminiUiState){
        is GeminiUiState.Success -> {
            result = (uiState as GeminiUiState.Success).outputText
        }
        is GeminiUiState.Error -> {
            result = "An error occurred. Check your prompt and try again."
        }
        is GeminiUiState.Loading -> {
            result = "Getting Response...."
        }
        is GeminiUiState.Initial -> {
            result = "Hello I am your assistant...."
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(uiState.geminiUiState != GeminiUiState.Success(""))
            ChatRow(chatText = result, isAI = true)
    }


}