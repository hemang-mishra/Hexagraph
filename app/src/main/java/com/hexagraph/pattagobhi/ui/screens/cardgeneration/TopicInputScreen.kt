package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.components.AppTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicInputScreen(
    viewModel: CardGenerationViewModel,
    onGenerateClick: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val decks by viewModel.listOfDecks.collectAsState(emptyList())
    val uiState by viewModel.uiState.collectAsState()
    TopicInputScreenBase(
        uiState = uiState.cardGenerationUIStateForUI,
        onTopicChange = { viewModel.updateUIStateForUI(topic = it) },
        onEasyQuestionsChange = { viewModel.updateUIStateForUI(easyQuestions = it) },
        onMediumQuestionsChange = { viewModel.updateUIStateForUI(mediumQuestions = it) },
        onHardQuestionsChange = { viewModel.updateUIStateForUI(hardQuestions = it) },
        onGenerateClick = onGenerateClick,
        onClickTextField = {
            isBottomSheetVisible = true
        }
    )
    if(isBottomSheetVisible){
        DeckSelectionBottomSheet(
            sheetState = bottomSheetState,
            decks = decks,
            onDeckSelected = {
                viewModel.selectDeck(it)
                isBottomSheetVisible = false
            }
        ) {
            isBottomSheetVisible = false
        }
    }
}

@Composable
private fun TopicInputScreenBase(
    uiState: CardGenerationUIStateForUI,
    onTopicChange: (String) -> Unit,
    onEasyQuestionsChange: (String) -> Unit,
    onMediumQuestionsChange: (String) -> Unit,
    onHardQuestionsChange: (String) -> Unit,
    onGenerateClick: () -> Unit,
    onClickTextField: () -> Unit
) {
    val scrollState = rememberScrollState()
    val localFocusManager = LocalFocusManager.current
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
            icon = Icons.Default.QuestionMark,
            onValueChange = onTopicChange,
            outerText = "Topic",
            placeholderText = "Enter topic",
            errorText = "Topic cannot be empty",
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                localFocusManager.moveFocus(FocusDirection.Down)
            })

        )

        // Number of Easy Questions
        AppTextField(
            value = uiState.easyQuestions,
            onValueChange = onEasyQuestionsChange,
            outerText = "Easy Questions",
            placeholderText = "Enter number of easy questions",
            errorText = "Must be a valid integer",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            icon = Icons.Default.Check,
            maxLines = 1,
            keyboardActions = KeyboardActions(onNext = {
                localFocusManager.moveFocus(FocusDirection.Down)
            })
        )

        // Number of Medium Questions
        AppTextField(
            value = uiState.mediumQuestions,
            onValueChange = onMediumQuestionsChange,
            outerText = "Medium Questions",
            placeholderText = "Enter number of medium questions",
            errorText = "Must be a valid integer",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            icon = Icons.Default.Lightbulb,
            maxLines = 1,
            keyboardActions = KeyboardActions(onNext = {
                localFocusManager.moveFocus(FocusDirection.Down)
            })
        )

        // Number of Hard Questions
        AppTextField(
            value = uiState.hardQuestions,
            onValueChange = onHardQuestionsChange,
            outerText = "Hard Questions",
            placeholderText = "Enter number of hard questions",
            errorText = "Must be a valid integer",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            icon = Icons.Default.Bolt,
            maxLines = 1,
            keyboardActions = KeyboardActions(onNext = {
                localFocusManager.moveFocus(FocusDirection.Down)
            })
        )

        AppTextField(
            value = uiState.deck.name,
            onValueChange = {},
            outerText = "Deck",
            placeholderText = "Select a deck",
            icon = Icons.Default.Folder,
            modifier = Modifier.clickable {
                onClickTextField()
            },
            isEnabled = false,
            maxLines = 1

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
