package com.hexagraph.pattagobhi.ui.screens.authentication.emailVerification

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.screens.authentication.login.HeadingOfLoginScreen
import kotlinx.coroutines.delay
import com.hexagraph.pattagobhi.R
import com.hexagraph.pattagobhi.ui.navigation.Screens

@Composable
fun LogInSuccess(navController: NavController) {

    Log.i("MainActivity", "onCreate: ${FirebaseAuth.getInstance().currentUser?.isEmailVerified}")
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
                .background(MaterialTheme.colorScheme.primary)

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
                        HeadingOfLoginScreen(largeText = "Yay! Login Successful", smallText = "")
                    }
                    item {
                        AnimatedVisibility(
                            visible = imageVisible,
                            enter = fadeIn() + scaleIn(), exit = fadeOut() + scaleOut()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.loginsuccesscenter),
                                contentDescription = null
                            )
                        }
                        if (!imageVisible)
                            Spacer(modifier = Modifier.height(300.dp))
                    }
                }
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                AppButton(
                    modifier = Modifier.width(314.dp),
                    text = "Next", isEnabled = true
                ) {
                    navController.navigate(Screens.NavHomeRoute)
                }
                Spacer(modifier = Modifier.height(24.dp))
                BottomRowOfLoginSuccess()
                Spacer(modifier = Modifier.height(52.dp))
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
                    colorFilter = ColorFilter.tint(Color(0xffeef6f8)),
                    modifier = Modifier
                        .requiredWidth(width = 99.dp)
                        .requiredHeight(height = 84.dp)
                )
            }
        }
    }
}

@Composable
//@Preview(showBackground = true)
fun VerificationSuccess(navController: NavController) {
    var imageVisible by remember {
        mutableStateOf(false)
    }
    Log.i("MainActivity", "onCreate: ${FirebaseAuth.getInstance().currentUser?.isEmailVerified}")
    LaunchedEffect(key1 = Unit) {
        delay(200)
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
                    .background(color = Color(0xffeef6f8))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        HeadingOfLoginScreen(largeText = "Verification Success", smallText = "")
                    }
                    item {
                        AnimatedVisibility(
                            visible = imageVisible,
                            enter = fadeIn() + scaleIn(), exit = fadeOut() + scaleOut()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.verificationsuccesscenter),
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
                            text = "Next", isEnabled = true
                        ) {
                            //Continue here with logic
                            navController.navigate(Screens.NavHomeRoute)
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
                    colorFilter = ColorFilter.tint(Color(0xffeef6f8)),
                    modifier = Modifier
                        .requiredWidth(width = 99.dp)
                        .requiredHeight(height = 84.dp)
                )
            }
        }
    }
}
