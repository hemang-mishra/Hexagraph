package com.hexagraph.pattagobhi.model

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.hexagraph.pattagobhi.R

enum class PermissionsRequired(
    val permission: String,
    val title: String,
    val permanentlyDeclinedText: String,
    val rationaleText: String,
    val image: Int
) {
    MICROPHONE_PERMISSION(
        permission = Manifest.permission.RECORD_AUDIO,
        title = "Microphone Permission Required",
        permanentlyDeclinedText = " Seems you have permanently declined this permission. Give access to this permission in settings",
        rationaleText = "Give access to this permission for Audio Recording.",
        image = R.drawable.baseline_library_music_24
    )

}