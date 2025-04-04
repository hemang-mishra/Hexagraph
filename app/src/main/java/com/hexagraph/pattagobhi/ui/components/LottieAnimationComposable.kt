package com.hexagraph.pattagobhi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.hexagraph.pattagobhi.R


@Composable
fun LottieAnimationComposable(
    modifier: Modifier,
    resource: Int,
    text: String? = null,
    isButtonEnabled: Boolean = false,
    speed: Float = 1f,
    onClick: () -> Unit = {}
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resource))
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition, iterations = 1000, speed = speed,
                modifier = Modifier.heightIn(min = 100.dp, max = 300.dp)
            )
            if (text != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = text)
            }
            if (isButtonEnabled) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onClick) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Composable
fun Test(){
    LottieAnimationComposable(modifier = Modifier, resource = R.raw.offlinelottie, text = "Turn on internet")
}