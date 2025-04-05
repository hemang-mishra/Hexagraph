package com.hexagraph.pattagobhi.ui.screens.onboarding

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.hexagraph.pattagobhi.model.PermissionsRequired
import com.hexagraph.pattagobhi.ui.components.AppButton
import com.hexagraph.pattagobhi.ui.navigation.MainActivity
import com.hexagraph.pattagobhi.ui.navigation.Screens


@Composable
fun PermissionScreen(viewModel: OnboardingHelper, navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
        if (viewModel.visiblePermissionDialogQueue.isEmpty() && !viewModel.isNavigationDone) {
            navController.navigate(Screens.NavHomeRoute)
            viewModel.isNavigationDone = true
        }
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Text(
                viewModel.visiblePermissionDialogQueue.size.toString() + " Permissions Missing!!",
                style = MaterialTheme.typography.headlineLarge
            )
            PermissionsRequired.entries.toList().forEach { permissionRequired ->

                AnimatedVisibility(
                    viewModel.visiblePermissionDialogQueue.contains(
                        permissionRequired
                    )
                ) {

                    val launcher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) {
                        viewModel.onPermissionInteractionResult(permissionRequired, it)
                        if (viewModel.visiblePermissionDialogQueue.isEmpty()) {
                            navController.navigate(Screens.NavHomeRoute)
                        }
                    }
                    PermissionCard(
                        permissionsRequired = permissionRequired,
                        isPermanentlyDeclined = shouldShowRequestPermissionRationale(
                            context as MainActivity,
                            permissionRequired.permission
                        ),
                        onOkClick = {
                            viewModel.dismissDialogue(permissionRequired)
                            launcher.launch(permissionRequired.permission)
                        },
                        onGoToAppSettingsClick = {
                            viewModel.dismissDialogue(permissionRequired)
                            context.openAppSettings()
                        },
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun PermissionCard(
    permissionsRequired: PermissionsRequired,
    isPermanentlyDeclined: Boolean,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = permissionsRequired.image,
                    contentDescription = "Image",

                    )
                Column {
                    Text(
                        permissionsRequired.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        if (!isPermanentlyDeclined)
                            permissionsRequired.rationaleText
                        else
                            permissionsRequired.permanentlyDeclinedText,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            AppButton(
                isEnabled = true,
                onClick = if (isPermanentlyDeclined) onGoToAppSettingsClick else onOkClick,
                text = if (isPermanentlyDeclined) "Go to Settings" else "Grant",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize(0.8f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
