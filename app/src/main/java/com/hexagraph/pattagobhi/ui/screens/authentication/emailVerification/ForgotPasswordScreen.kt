package com.hexagraph.pattagobhi.ui.screens.authentication.emailVerification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.navigation.AuthenticationNavigation
import com.hexagraph.pattagobhi.ui.screens.authentication.login.HeadingOfLoginScreen
import kotlinx.coroutines.delay
import com.hexagraph.pattagobhi.R


@Composable
fun ForgotPassword(navController: NavController) {
    var imageVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        delay(500)
        imageVisible = true
    }
    Surface(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .fillMaxHeight(0.77f)
                    .clip(shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        HeadingOfLoginScreen(
                            largeText = "Password reset link sent!",
                            smallText = "Please check your inbox"
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    item {
                        AnimatedVisibility(
                            visible = imageVisible,
                            enter = fadeIn() + scaleIn(), exit = fadeOut() + scaleOut()
                        ) {
                            Image(
                                imageVector = Icons.Default.Password,
                                contentDescription = null
                            )
                        }
                        if (!imageVisible)
                            Spacer(modifier = Modifier.height(300.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.height(60.dp))
                        AppButton(
                            modifier = Modifier.width(314.dp),
                            text = "Go back to login", isEnabled = true
                        ) {
                            navController.navigate(AuthenticationNavigation.LoginScreen)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        BottomRowOfLoginSuccess()
                    }

                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.07f))
                Image(
                    painter = painterResource(id = R.drawable.bird_icon_bg_remove),
                    contentDescription = "logo + name",
//                    colorFilter = ColorFilter.tint(Color(0xffeef6f8)),
                    modifier = Modifier
                        .requiredWidth(width = 99.dp)
                        .requiredHeight(height = 84.dp)
                )
            }
        }
    }
}

@Composable
fun BottomRowOfLoginSuccess() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = "Need help ",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF454547),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = "FAQ",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF454547),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = "Term of use ",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF454547),
                textAlign = TextAlign.Center,
            )
        )
    }
}