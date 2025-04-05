package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexagraph.pattagobhi.R
import com.hexagraph.pattagobhi.ui.components.LottieAnimationComposable
import com.hexagraph.pattagobhi.ui.screens.chat.BotScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainCardGenerationScreen(
    viewModel: CardGenerationViewModel = hiltViewModel(),
    deckId: Int? = null,
    snackbarHostState: SnackbarHostState
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deckId) {
        if(deckId!= null){
            viewModel.initializeReviewScreen(deckId)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        AnimatedContent(
            targetState = uiState.currentScreen,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            modifier = Modifier.padding(padding)
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
                    ReviewScreen(viewModel)
                }

                CurrentScreen.ChatScreen -> {
                    BotScreen(
                        initialPrompt = uiState.prompt,
                        onGoBack = {
                            viewModel.switchScreen(
                                uiState.previousScreen ?: CurrentScreen.ChatScreen
                            )
                        })
                }

                CurrentScreen.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LottieAnimationComposable(
                            modifier = Modifier.fillMaxSize(),
                            resource = R.raw.loading_animation
                        )
                    }
                }
            }
        }
    }
}
