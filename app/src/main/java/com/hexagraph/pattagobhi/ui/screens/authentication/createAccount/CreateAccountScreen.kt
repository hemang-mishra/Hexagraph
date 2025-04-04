package com.hexagraph.pattagobhi.ui.screens.authentication.createAccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.components.AppTextField
import com.hexagraph.pattagobhi.ui.components.HeadingOfCreateAccount
import com.hexagraph.pattagobhi.ui.navigation.AuthenticationNavigation
import com.hexagraph.pattagobhi.util.emailCheck
import androidx.compose.runtime.getValue
import com.hexagraph.pattagobhi.R


@Composable
fun CreateAccountEmailScreen(viewModel: CreateAccountViewModel, navController: NavController) {
    val uiState by viewModel.uiState
    if (!uiState.isEmailValid) {
        emailCheck(uiState.email, viewModel::onEmailErrorStateChange)
    }
    Scaffold(
    ) {
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
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    item {
                        HeadingOfCreateAccount(
                            modifier = Modifier
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(40.dp))
                        Image(
                            painter = painterResource(id = R.drawable.done),
                            contentDescription = "done",
                            modifier = Modifier
                                .requiredSize(size = 222.dp)
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.width(8.dp))
                        AppTextField(
                            modifier = Modifier,
                            value = uiState.email,
                            onValueChange = viewModel::onEmailChange,
                            isError = !uiState.isEmailValid,
                            errorText = uiState.emailError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        AppButton(
                            modifier = Modifier.width(314.dp),
                            isEnabled = true
                        ) {
                            if (emailCheck(uiState.email, viewModel::onEmailErrorStateChange)) {
                                return@AppButton
                            }
                            navController.navigate(AuthenticationNavigation.CreateAccountPasswordScreen)
                        }
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
