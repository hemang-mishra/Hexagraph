package com.hexagraph.pattagobhi.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.navigation.AuthenticationNavigation
import com.hexagraph.pattagobhi.R

@Composable
fun OnBoardingScreen(navController: NavController) {
    var screen by remember {
        mutableIntStateOf(1)
    }
    Scaffold() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Spacer(modifier = Modifier.height(16.dp))
                Logo(
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(min = 300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(160.dp))
                AnimatedCenterBox(
                    screen = screen, modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
            ) {
                AppButton(text = "Next", isEnabled = true, modifier = Modifier.fillMaxWidth(0.8f)) {
                    when (screen) {
                        1 -> {
                            screen++
                        }

                        2 -> screen++
                        3 -> {
                            navController.navigate(AuthenticationNavigation.LoginScreen)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}


@Composable
private fun Logo(modifier: Modifier) {

    Image(
        painter = painterResource(id = R.drawable.owl_white),
        contentDescription = null,
        modifier = modifier
            .padding(1.dp)
            .size(80.dp)
    )
}


@Composable
private fun ImageGroupS1(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.welcome_splash),
        contentDescription = null,
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
    )
}

@Composable
private fun GroupCenterS1(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ImageGroupS1(modifier = modifier)
    }
}

@Composable
private fun GroupCenterS2(modifier: Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
//        Image(
//            painter = painterResource(id = R.drawable.backs2), contentDescription = null,
//            modifier = Modifier.fillMaxWidth()
//        )
        Image(
            painter = painterResource(id = R.drawable.welcome_splash),
            contentDescription = null,
            modifier = modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
        )
    }
}

@Composable
private fun GroupCenterS3(modifier: Modifier) {
    Box(modifier = modifier) {
//        Image(painter = painterResource(id = R.drawable.backs3), contentDescription = null)
        Image(
            painter = painterResource(id = R.drawable.welcome_splash),
            contentDescription = null,
            modifier = modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
        )
    }
}

@Composable
private fun NonAnimatedCenterS1(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupCenterS1(
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.dots11),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to MindStack",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontWeight = FontWeight(600)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Empower your learning journey by creating personalized flashcards tailored to your study needs. Let's get started!",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.width(330.dp)
        )
    }
}

@Composable
private fun NonAnimatedCenterS2(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupCenterS2(
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.dots1),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Create Flashcards with Ease",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontWeight = FontWeight(600),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Simply enter your topic, choose the number of flashcards, and customize difficulty levels to generate your perfect study set.",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.width(330.dp)
        )
    }
}


@Composable
private fun NonAnimatedCenterS3(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupCenterS3(
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.dots3),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Study On-the-Go",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontWeight = FontWeight(600),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Access your flashcards anytime, anywhere, and track your progress to achieve your learning goals.",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.width(330.dp)
        )
    }
}

@Composable
private fun AnimatedCenterBox(screen: Int, modifier: Modifier) {
    Box {
        AnimatedContent(
            targetState = screen,
            label = "",
            transitionSpec = {
                slideIntoContainer(
                    animationSpec = tween(100, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                ).togetherWith(
                    slideOutOfContainer(
                        animationSpec = tween(100, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.Left
                    )
                )
            }
        ) { targetState ->
            when (targetState) {
                1 -> NonAnimatedCenterS1(modifier = modifier)
                2 -> NonAnimatedCenterS2(modifier = modifier)
                3 -> NonAnimatedCenterS3(modifier = modifier)
            }
        }
    }
}