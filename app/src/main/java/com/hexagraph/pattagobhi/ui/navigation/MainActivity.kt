package com.hexagraph.pattagobhi.ui.navigation

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.hexagraph.pattagobhi.model.PermissionsRequired
import com.hexagraph.pattagobhi.ui.screens.onboarding.OnboardingHelper
import com.hexagraph.pattagobhi.ui.theme.HexagraphTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HexagraphTheme {
                val snackbarHostState = remember() {
                    SnackbarHostState()
                }
                // A surface container using the 'background' color from the theme
//                Scaffold(
//                    modifier = Modifier.Companion.fillMaxSize()
//
//                ) { paddingvalues ->
                    Box(modifier = Modifier) {
                        AppNavigation(
                            snackbarHostState = snackbarHostState,
                            onboardingViewModel = OnboardingHelper
                        )
                    }
//                }
            }
        }
    }

    private fun refreshPermissionsStatus(onboardingViewModel: OnboardingHelper) {
        PermissionsRequired.entries.forEach {
            if (ContextCompat.checkSelfPermission(
                    this,
                    it.permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                onboardingViewModel.visiblePermissionDialogQueue.remove(it)

            } else {
                if (!onboardingViewModel.visiblePermissionDialogQueue.contains(it)) {
                    onboardingViewModel.visiblePermissionDialogQueue.add(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("check", "On resume")
        refreshPermissionsStatus(OnboardingHelper)
    }
}