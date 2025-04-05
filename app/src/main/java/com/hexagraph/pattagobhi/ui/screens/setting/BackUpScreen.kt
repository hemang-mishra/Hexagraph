package com.hexagraph.pattagobhi.ui.screens.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexagraph.pattagobhi.ui.components.AppButton

@Composable
fun BackUpScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
) {
    val isLoading by viewModel.isBackUpRestoreInProgress
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it), contentAlignment = Alignment.Center) {
            Spacer(modifier = Modifier.height(52.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("BackUp Screen", fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(16.dp))
                AppButton(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = "BackUp",
                    isEnabled = !isLoading,
                    onClick = {
                        viewModel.backUp() {
                            onBackPress()
                        }
                    }
                )
                Spacer(Modifier.height(16.dp))
                AppButton(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    isEnabled = !isLoading,
                    text = "Restore",
                    onClick = {
                        viewModel.restoreData() {
                            onBackPress()
                        }
                    }
                )
            }
        }
    }
}