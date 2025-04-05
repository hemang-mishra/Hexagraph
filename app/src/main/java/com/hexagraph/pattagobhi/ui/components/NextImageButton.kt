package com.hexagraph.pattagobhi.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hexagraph.pattagobhi.R
import kotlinx.coroutines.delay

@Composable
fun NextImageButton(modifier: Modifier, smallSize: Int = 50, largeSize: Int = 71, onClick: () -> Unit) {
    var isSizeReduced by remember { mutableStateOf<Boolean?>(null) }
    val imageSize = animateDpAsState(if (isSizeReduced == true) smallSize.dp else largeSize.dp)

    LaunchedEffect(key1 = isSizeReduced) {
        if (isSizeReduced == true) {
            delay(10L) // delay for 1 second
            isSizeReduced = null
        }
    }

    Image(
        painter = painterResource(id = R.drawable.img_1),
        contentDescription = "image description",
        contentScale = ContentScale.None,
        modifier = modifier
            .padding(1.dp)
            .width(imageSize.value)
            .height(imageSize.value)
            .clip(CircleShape)
            .clickable {
                isSizeReduced = true
                onClick()
            }
    )
}