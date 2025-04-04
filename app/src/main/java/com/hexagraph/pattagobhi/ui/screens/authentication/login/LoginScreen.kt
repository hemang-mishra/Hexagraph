package com.hexagraph.pattagobhi.ui.screens.authentication.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hexagraph.pattagobhi.util.emailCheck
import com.hexagraph.pattagobhi.util.passwordCheck
import androidx.compose.runtime.getValue
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.components.AppTextField
import com.hexagraph.pattagobhi.ui.components.DividerWithText
import com.hexagraph.pattagobhi.ui.components.Wait
import com.hexagraph.pattagobhi.ui.navigation.AuthenticationNavigation
import com.hexagraph.pattagobhi.R


@Composable
fun LogInScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val uiState by viewModel.uiState
    if (!uiState.isEmailValid) {
        emailCheck(
            email = uiState.email, error = viewModel::onEmailErrorStateChange
        )
    }
    if (!uiState.isPasswordValid) {
        passwordCheck(password = uiState.password, error = viewModel::onPasswordErrorStateChange)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.primary
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .fillMaxHeight(0.77f)
                    .clip(shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
            ) {
                if (uiState.isLoading)
                    Wait()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        HeadingOfLoginScreen(
                            largeText = stringResource(R.string.let_s_get_started),
                            smallText = stringResource(R.string.lets_login_for_explore_continues)
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    item {
                        AppTextField(
                            modifier = Modifier,
                            value = uiState.email,
                            onValueChange = viewModel::onEmailChange,
                            outerText = "Login with email",
                            placeholderText = "Enter your email",
                            icon = Icons.Default.Email,
                            isError = !uiState.isEmailValid,
                            errorText = uiState.emailError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        AppTextField(
                            modifier = Modifier,
                            value = uiState.password,
                            onValueChange = viewModel::onPasswordChange,
                            outerText = "Password",
                            placeholderText = "Enter your password",
                            icon = Icons.Default.Password,
                            isPassword = true,
                            isError = !uiState.isPasswordValid,
                            errorText = uiState.passwordError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        Text(
                            text = "Forgot password?",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 70.sp,
                                fontWeight = FontWeight(500),
                                textAlign = TextAlign.Right,
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .width(314.dp)
                                .clickable {
                                    if (!emailCheck(
                                            email = uiState.email,
                                            error = viewModel::onEmailErrorStateChange
                                        )
                                    ) {
                                        viewModel.forgotPassword(snackbarHostState, navController) {
                                            navController.navigate(AuthenticationNavigation.ForgotPasswordScreen)
                                        }
                                    }
                                }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        AppButton(
                            modifier = Modifier.width(314.dp),
                            text = "Sign in",
                            isEnabled = !uiState.isLoading
                        ) {
                            if (emailCheck(
                                    email = uiState.email,
                                    error = viewModel::onEmailErrorStateChange
                                )
                                || passwordCheck(
                                    password = uiState.password,
                                    error = viewModel::onPasswordErrorStateChange
                                )
                            ) {
                                return@AppButton
                            }
                            viewModel.login(snackbarHostState, navController) {
                                navController.navigate(AuthenticationNavigation.LoginSuccessScreen)
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        DividerWithText(
                            modifier = Modifier.width(314.dp),
                            text = stringResource(R.string.you_can_connect_with)
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.btnsigninwithgoogle),
                            contentDescription = stringResource(R.string.image_description),
                            contentScale = ContentScale.None,
                            modifier = Modifier
                                .padding(0.dp)
                                .width(44.dp)
                                .height(44.00044.dp)
                                .clickable {
                                    viewModel.googleLogIn(context, snackbarHostState) {
                                        navController.navigate(AuthenticationNavigation.LoginSuccessScreen)
                                    }
                                }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        BottomRowOfLoginScreen(onSignUpClick = {
                            navController.navigate(AuthenticationNavigation.CreateAccountScreen)
                        })
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
                    contentDescription = stringResource(R.string.logo_name),
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
private fun BottomRowOfLoginScreen(onSignUpClick: () -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.don_t_have_an_account),
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
            )
        )
        TextButton(onClick = onSignUpClick) {
            Text(text = stringResource(id = R.string.sign_up_here),
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                ),
            )
        }
//        Text(
//            text = stringResource(R.string.sign_up_here),
//
//            // smallest 12
//            style = TextStyle(
//                fontSize = 12.sp,
//                lineHeight = 20.sp,
//                fontFamily = bodyFontFamily,
//                fontWeight = FontWeight(500),
//                color = Color(0xFFFB8A7A),
//                textAlign = TextAlign.Center,
//            ),
//            modifier = Modifier.clickable { onSignUpClick() }
//        )
    }
}

@Composable
fun HeadingOfLoginScreen(largeText: String, smallText: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = largeText,

            // heading large 24
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontWeight = FontWeight(600),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = smallText,

            // content 16
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
            )
        )
    }
}
