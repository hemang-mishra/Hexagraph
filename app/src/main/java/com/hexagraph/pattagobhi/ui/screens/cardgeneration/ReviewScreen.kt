package com.hexagraph.pattagobhi.ui.screens.cardgeneration


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hexagraph.pattagobhi.Entity.Card
import com.hexagraph.pattagobhi.R
import com.hexagraph.pattagobhi.ui.theme.HexagraphTheme
import com.hexagraph.pattagobhi.util.Review

@Composable
fun ReviewScreen(viewModel: CardGenerationViewModel, goToHomeScreen: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    ReviewScreenBase(
        uiState = uiState,
        onMenuClick = {},
        onBackClick = {
            viewModel.goToAParticularCard(uiState.reviewScreenUIState.currentIndex - 1)
        },
        onMuteClick = {
            viewModel.toggleSpeakerState(!uiState.reviewScreenUIState.isTextToSpeechActive)
        },
        onMicClick = {},
        onCloseClick = {
            viewModel.goToAParticularCard(uiState.reviewScreenUIState.currentIndex + 1)
        },
        onHelpClick = {
            viewModel.switchScreen(CurrentScreen.ChatScreen)
        },
        onShowAnswerClick = {
            viewModel.switchReviewScreenState(CurrentStateOfReviewScreen.AnswerIsDisplayed)
        },
        goToIndex = { index ->
            viewModel.goToAParticularCard(index)
        },
        viewModel,
        goToHomeScreen
    )

}

@Composable
fun ReviewScreenBase(
    uiState: CardGenerationUIState,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    onMuteClick: () -> Unit,
    onMicClick: () -> Unit,
    onCloseClick: () -> Unit,
    onHelpClick: () -> Unit,
    onShowAnswerClick: () -> Unit,
    goToIndex: (Int) -> Unit,
    viewModel: CardGenerationViewModel,
    goToHomeScreen: () -> Unit
) {
    val verticalPager = rememberPagerState {
        uiState.easyCards.size + uiState.mediumCards.size + uiState.hardCards.size
    }
    var isScrolling = false
    LaunchedEffect(uiState.reviewScreenUIState.currentIndex) {
        isScrolling = true
        if (verticalPager.currentPage != uiState.reviewScreenUIState.currentIndex) {
            verticalPager.animateScrollToPage(uiState.reviewScreenUIState.currentIndex)
        }
        isScrolling = false
    }
    LaunchedEffect(verticalPager.currentPage) {
        if (verticalPager.currentPage != uiState.reviewScreenUIState.currentIndex)
            goToIndex(verticalPager.currentPage)
    }
    val currentScreenSize = getScreenHeightInDp()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopHeaderSection(
            isSpeakerActive = uiState.reviewScreenUIState.isTextToSpeechActive,
            onMenuClick, onBackClick, onMuteClick, onCloseClick
        )
        CardsLeftIndicator(
            cardsLeftText = "${uiState.easyCards.size + uiState.mediumCards.size + uiState.hardCards.size - verticalPager.currentPage - 1} cards left",
            modifier = Modifier.padding(16.dp)
        )
        VerticalPager(
            state = verticalPager,
            modifier = Modifier.fillMaxWidth()
        ) {
//            if(uiState.reviewScreenUIState.currentIndex != it && it == verticalPager.currentPage && !isScrolling)
//                goToIndex(it)
            val card = uiState.easyCards.getOrNull(it)
                ?: uiState.mediumCards.getOrNull(it - uiState.easyCards.size)
                ?: uiState.hardCards.getOrNull(it - uiState.easyCards.size - uiState.mediumCards.size)
            Box(modifier = Modifier.height((currentScreenSize - 60).dp)) {
                Column {
                    if (card != null) {
                        MainCardSection(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            questionText = card.question,
                            onMicClick = onMicClick,
                            answerText = card.answer,
                            feedbackText = uiState.reviewScreenUIState.feedbackText ?: "",
                            voiceText = uiState.reviewScreenUIState.voiceText,
                            onHelpClick = onHelpClick,
                            shouldShowAnswer = if (uiState.reviewScreenUIState.currentState == CurrentStateOfReviewScreen.AnswerIsDisplayed ||
                                uiState.reviewScreenUIState.currentState == CurrentStateOfReviewScreen.AnswerIsDisplayedWithFeedback
                            ) true else false,
                        )
                        if (uiState.reviewScreenUIState.currentState == CurrentStateOfReviewScreen.OnlyQuestionDisplayed)
                            BottomBarSection(
                                buttonText = "Show Answer",
                                onClick = onShowAnswerClick
                            ) else {
                            ReviewButtons(
                                listOf(
                                    viewModel.nextReviewTime(
                                        card.reviewRecord.size,
                                        Review.HARD
                                    ),
                                    viewModel.nextReviewTime(card.reviewRecord.size, Review.MEDIUM),
                                    viewModel.nextReviewTime(card.reviewRecord.size, Review.EASY)
                                ), viewModel, card, it,
                                goToHomeScreen
                            )
                        }
                    }
                }
            }
        }
//        LazyColumn(
//           state = lazyColumState,
//            modifier = Modifier.fillMaxWidth()
//
//        ) {
//            items(uiState.easyCards.size+uiState.mediumCards.size+uiState.hardCards.size) {
//                currentIndex = it
//                val card = uiState.easyCards.getOrNull(it)
//                    ?: uiState.mediumCards.getOrNull(it - uiState.easyCards.size)
//                    ?: uiState.hardCards.getOrNull(it - uiState.easyCards.size - uiState.mediumCards.size)
//                Box(modifier = Modifier.height((currentScreenSize-60).dp)) {
//                    Column {
//                        if (card != null) {
//                            MainCardSection(
//                                modifier = Modifier.padding(horizontal = 16.dp),
//                                questionText = card.question,
//                                onMicClick = onMicClick,
//                                answerText = card.answer,
//                                feedbackText = uiState.reviewScreenUIState.feedbackText?:"",
//                                voiceText = uiState.reviewScreenUIState.voiceText,
//                                onHelpClick = onHelpClick,
//                                shouldShowAnswer = if(uiState.reviewScreenUIState.currentState == CurrentStateOfReviewScreen.AnswerIsDisplayed ||
//                                    uiState.reviewScreenUIState.currentState == CurrentStateOfReviewScreen.OnlyQuestionDisplayed) true else false,
//                            )
//                            if (uiState.reviewScreenUIState.currentState == CurrentStateOfReviewScreen.OnlyQuestionDisplayed)
//                                BottomBarSection(
//                                    buttonText = "Show Answer",
//                                    onClick = onShowAnswerClick
//                                ) else {
//                                ReviewButtons(listOf("1m", "2m", "3m", "5m"))
//                            }
//                        }
//                    }
//                }
//            }
    }
}

@Composable
fun CardsLeftIndicator(
    cardsLeftText: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.secondary,
    paddingStart: Dp = 16.dp,
    paddingTop: Dp = 8.dp
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = paddingStart, top = paddingTop),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = cardsLeftText,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun TopHeaderSection(
    isSpeakerActive: Boolean,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    onMuteClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    iconSize: Dp = 24.dp,
    padding: Dp = 12.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(padding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Icon: Hamburger Menu
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )
        }

        // Right Icons: Back, Mute, Close
        Row {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Back",
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            }

            IconButton(onClick = onMuteClick) {
                Icon(
                    imageVector = if (isSpeakerActive) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Mute",
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            }

            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.QuestionMark,
                    contentDescription = "AI",
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}


@Composable
fun MainCardSection(
    questionText: String,
    answerText: String,
    feedbackText: String,
    voiceText: String?,
    onHelpClick: () -> Unit,
    onMicClick: () -> Unit,
    shouldShowAnswer: Boolean,
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = Color(0xFF393C3F),
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconTint: Color = Color.Blue,
    iconSize: Dp = 48.dp,
    questionTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize().border(2.dp, Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp)),
        ) {
            // Scrollable Column for content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(0.75f).padding(top = 8.dp),
                        text = questionText,
                        style = questionTextStyle,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    IconButton(onClick = onHelpClick, colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF5A6A79))) {
                        Icon(
                            imageVector = Icons.Default.QuestionMark,
                            modifier = Modifier.size(20.dp),
                            contentDescription = null
                        )
                    }
                }

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Additional content can go here or leave blank as per current design
                if (shouldShowAnswer) {
                    Text(
                        text = answerText,
                        style = questionTextStyle,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = feedbackText,
                        style = questionTextStyle,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                if (voiceText != null) {
                    Text(
                        text = voiceText,
                        style = questionTextStyle,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Mic/Play Icon at Bottom Center
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                IconButton(onClick = onMicClick) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Play or Mic",
                        tint = iconTint,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    }
}


@Composable
fun BottomBarSection(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.DarkGray.copy(alpha = 0.7f),
    contentColor: Color = Color.White,
    cornerRadius: Dp = 24.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadius))
                .padding(top = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = contentColor
            )
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
fun ReviewButtons(
    times: List<String>,
    viewModel: CardGenerationViewModel,
    card: Card,
    cardIdx: Int,
    goToHomeScreen: () -> Unit
) {
    val labels = listOf("Hard", "Good", "Easy")
    val backgroundColors = listOf(
        Color(0xAA861A1A),
        Color(0xAAC68C1D), // mustard yellow with transparency
        //Color(0xAA2E4F26), // dark green with transparency
        Color(0xAA3E4E5C)  // dark blue-gray with transparency
    )
    val borderColors = listOf(
        Color(0xFFEA3860),
        Color(0xFFE6B800),
       // Color(0xFF88D957),
        Color(0xFFA2C3DB)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        labels.indices.forEach { i ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1.2f) // keeps button roughly squareish, adjust as needed
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(backgroundColors[i])
                    .border(2.dp, borderColors[i], RoundedCornerShape(16.dp))
                    .clickable {
                        viewModel.addCard(i, card, cardIdx)
                        if (cardIdx == 0) {
                            goToHomeScreen()
                        }
                    }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = labels[i],
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = times.getOrNull(i) ?: "1m",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun getScreenHeightInDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}

//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun ReviewScreenPreview(){
//    HexagraphTheme {
//        ReviewScreenBase(
//            uiState = CardGenerationUIState(
//                easyQuestions = listOf(
//                    "What is your name?",
//                    "What is your age?",
//                    "What is your favorite color?"
//                ),
//                easyCards = listOf(
//                    Card(
//                        question = "What is your name?",
//                        answer = "John Doe",
//                        deckId = 2,
//                    )
//                ),
//            ),
//            onMenuClick = {},
//            onBackClick = { },
//            onMuteClick = { },
//            onMicClick = { },
//            onCloseClick = { },
//            onHelpClick = {},
//            goToIndex = {},
//            onShowAnswerClick = {  }
//        )
//    }
//}
