package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexagraph.pattagobhi.ui.screens.chat.BotScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainCardGenerationScreen(
    viewModel: CardGenerationViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimatedContent(
        targetState = uiState.currentScreen,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) { targetScreen ->
        when (targetScreen) {
            CurrentScreen.TopicInputScreen -> {
                TopicInputScreen(
                    viewModel = viewModel,
                    onGenerateClick = {
                        viewModel.generateQuestions(
                            uiState.cardGenerationUIStateForUI.topic,
                            uiState.cardGenerationUIStateForUI.easyQuestions.toInt(),
                            uiState.cardGenerationUIStateForUI.mediumQuestions.toInt(),
                            uiState.cardGenerationUIStateForUI.hardQuestions.toInt(),
                        )
                    }
                )
            }
            CurrentScreen.ReviewScreen -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    uiState.easyQuestions.forEach(){
                        Text(it)
                    }
                    uiState.mediumQuestions.forEach(){
                        Text(it)
                    }
                    uiState.hardQuestions.forEach(){
                        Text(it)
                    }
                }
            }
            CurrentScreen.ChatScreen -> {
                BotScreen()
            }

            CurrentScreen.Loading -> {

            }
        }
    }
}