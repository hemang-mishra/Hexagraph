package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.components.AppTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TopicInputScreen(
    viewModel: CardGenerationViewModel,
    onGenerateClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    TopicInputScreenBase(
        uiState = uiState.cardGenerationUIStateForUI,
        onTopicChange = { viewModel.updateUIStateForUI(topic = it) },
        onEasyQuestionsChange = { viewModel.updateUIStateForUI(easyQuestions = it) },
        onMediumQuestionsChange = { viewModel.updateUIStateForUI(mediumQuestions = it) },
        onHardQuestionsChange = { viewModel.updateUIStateForUI(hardQuestions = it) },
        onGenerateClick = onGenerateClick
    )
}

@Composable
private fun TopicInputScreenBase(
    uiState: CardGenerationUIStateForUI,
    onTopicChange: (String) -> Unit,
    onEasyQuestionsChange: (String) -> Unit,
    onMediumQuestionsChange: (String) -> Unit,
    onHardQuestionsChange: (String) -> Unit,
    onGenerateClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Generate FlashCards",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Topic input field
        AppTextField(
            value = uiState.topic,
            icon = Icons.Default.Lightbulb,
            onValueChange = onTopicChange,
            outerText = "Topic",
            placeholderText = "Enter topic",
            errorText = "Topic cannot be empty"
        )

        // Number of Easy Questions
        AppTextField(
            value = uiState.easyQuestions,
            onValueChange = onEasyQuestionsChange,
            outerText = "Easy Questions",
            placeholderText = "Enter number of easy questions",
            errorText = "Must be a valid integer",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Number of Medium Questions
        AppTextField(
            value = uiState.mediumQuestions,
            onValueChange = onMediumQuestionsChange,
            outerText = "Medium Questions",
            placeholderText = "Enter number of medium questions",
            errorText = "Must be a valid integer",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Number of Hard Questions
        AppTextField(
            value = uiState.hardQuestions,
            onValueChange = onHardQuestionsChange,
            outerText = "Hard Questions",
            placeholderText = "Enter number of hard questions",
            errorText = "Must be a valid integer",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Generate button, enabled when topic is provided and all question fields are valid.
        AppButton(
            text = "Generate",
            isEnabled = uiState.topic.isNotEmpty() &&
                    uiState.isEasyQuestionsValid &&
                    uiState.isMediumQuestionsValid &&
                    uiState.isHardQuestionsValid,
            onClick = onGenerateClick
        )
    }
}
