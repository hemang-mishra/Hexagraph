package com.hexagraph.pattagobhi.ui.navigation

import kotlinx.serialization.Serializable

interface AuthenticationNavigation {
    @Serializable
    object LoginScreen : AuthenticationNavigation

    @Serializable
    object CreateAccountScreen : AuthenticationNavigation

    @Serializable
    data class EmailVerificationScreen(val email: String, val password: String) :
        AuthenticationNavigation

    @Serializable
    object ForgotPasswordScreen : AuthenticationNavigation

    @Serializable
    object LoginSuccessScreen : AuthenticationNavigation

    @Serializable
    object CreateAccountPasswordScreen : AuthenticationNavigation

    @Serializable
    data class CardScreen(val deckId: Int,val name : String) : AuthenticationNavigation

    @Serializable
    object TopicInputScreen : AuthenticationNavigation

    @Serializable
    data class AddCardScreen(val deckId: Int): AuthenticationNavigation
}