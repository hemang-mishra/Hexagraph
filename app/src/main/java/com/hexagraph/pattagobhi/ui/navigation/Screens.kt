package com.hexagraph.pattagobhi.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screens{
    @Serializable
    data object NavHomeRoute

    @Serializable
    data object NavLoadingScreen

    @Serializable
    data object NavOnboardingScreen

    @Serializable
    data object NavPermissionsScreen

    @Serializable
    data object NavBackUpScreen

}