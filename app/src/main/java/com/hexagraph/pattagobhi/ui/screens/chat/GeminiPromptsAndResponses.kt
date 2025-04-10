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
    fun generateAnswerPrompt(topic: String, question: String, difficulty: String): String{
        val numberOfWorkds = when(difficulty){
            "Medium"-> 30
            "Easy" -> 15
            "Hard" -> 50
            else -> 30
        }
        return "Topic: $topic\n" +
                "\n" +
                "Difficulty: $difficulty" +
                "\n" +
                "$question\n" +
                "\n" +
                "Answer for this question in $numberOfWorkds to ${numberOfWorkds+5} words.\n" +
                "\n" +
                "Explanation should be as simple and accurate as possible\n" +
                "\n" +
                "Respond only the answer.\n" +
                "You can format your response in markdown"+
                "\n" +
                "no unnecessary text"
    }
    fun generateHelpPrompt(answer: String, question: String): String{
        return "Question : $question\n" +
                "\n" +
                "Answer : $answer.\n" +
                "\n" +
                "Elaborate your answer. Explain it like explaining to a child. Mention specific term definitions as well.\n" +
                "\n" +
                "Use between 100 to 150 words.\n" +
                "\n" +
                "No unnecessary text. Answer and text only."
    }
    fun feedbackOnResponse(question: String, answer: String, response: String): String{
        return "Question : $question\n" +
                "\n" +
                "\n" +
                "The user responded with $response.\n" +
                "Provide a feedback on the response. Judge whether it is correct or not."+
                "\n" +
                "Use between 100 to 150 words.\n" +
                "You can format your response in markdown"+
                "\n" +
                "No unnecessary text. Answer and text only."
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