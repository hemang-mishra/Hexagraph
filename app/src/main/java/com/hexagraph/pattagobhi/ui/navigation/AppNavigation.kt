package com.hexagraph.pattagobhi.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.auth.FirebaseAuth
import com.hexagraph.pattagobhi.ui.screens.authentication.createAccount.CreateAccountEmailScreen
import com.hexagraph.pattagobhi.ui.screens.authentication.createAccount.CreateAccountPasswordScreen
import com.hexagraph.pattagobhi.ui.screens.authentication.createAccount.CreateAccountViewModel
import com.hexagraph.pattagobhi.ui.screens.authentication.emailVerification.EmailVerificationViewModel
import com.hexagraph.pattagobhi.ui.screens.authentication.emailVerification.ForgotPassword
import com.hexagraph.pattagobhi.ui.screens.authentication.emailVerification.LogInSuccess
import com.hexagraph.pattagobhi.ui.screens.authentication.emailVerification.VerifyEmail
import com.hexagraph.pattagobhi.ui.screens.authentication.login.LogInScreen
import com.hexagraph.pattagobhi.ui.screens.authentication.login.LoginViewModel
import com.hexagraph.pattagobhi.ui.screens.chat.BotScreen
import com.hexagraph.pattagobhi.ui.screens.home.HomeScreen
import com.hexagraph.pattagobhi.ui.screens.onboarding.OnBoardingScreen
import com.hexagraph.pattagobhi.ui.screens.onboarding.OnboardingHelper
import com.hexagraph.pattagobhi.ui.screens.onboarding.PermissionScreen

@Composable
fun AppNavigation(
    navController: NavController = rememberNavController(),
    onboardingViewModel: OnboardingHelper,
    snackbarHostState: SnackbarHostState,
){
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val emailVerificationViewModel = hiltViewModel<EmailVerificationViewModel>()
    val createAccountViewModel = hiltViewModel<CreateAccountViewModel>()

    NavHost(
        navController = navController as NavHostController,
        startDestination = if (FirebaseAuth.getInstance().currentUser != null){
            if(onboardingViewModel.areAllPermissionsGranted())
                Screens.NavHomeRoute
            else Screens.NavPermissionsScreen
        }
             else Screens.NavOnboardingScreen
    ){
        composable<Screens.NavHomeRoute> {
            BotScreen(navController)
        }
        composable<Screens.NavPermissionsScreen> {
            PermissionScreen(viewModel = onboardingViewModel, navController)
        }
        composable<Screens.NavOnboardingScreen> {
            OnBoardingScreen(navController)
        }
        composable<AuthenticationNavigation.LoginScreen> {
            LogInScreen(viewModel = loginViewModel, navController = navController)
        }
        composable<AuthenticationNavigation.LoginSuccessScreen> {
            LogInSuccess(navController = navController)
        }
        composable<AuthenticationNavigation.CreateAccountScreen> {
            CreateAccountEmailScreen(
                viewModel = createAccountViewModel,
                navController = navController
            )
        }
        composable<AuthenticationNavigation.CreateAccountPasswordScreen> {
            CreateAccountPasswordScreen(
                viewModel = createAccountViewModel,
                navController = navController
            )
        }
        composable<AuthenticationNavigation.EmailVerificationScreen> {
            val arg = it.toRoute<AuthenticationNavigation.EmailVerificationScreen>()
            VerifyEmail(
                viewModel = emailVerificationViewModel,
                navController = navController,
                email = arg.email,
                password = arg.password
            )
        }
        composable<AuthenticationNavigation.ForgotPasswordScreen> {
            ForgotPassword(navController = navController)
        }
    }
}