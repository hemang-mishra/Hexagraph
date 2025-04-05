package com.hexagraph.pattagobhi.model

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector

enum class PermissionsRequired(
    val permission: String,
    val title: String,
    val permanentlyDeclinedText: String,
    val rationaleText: String,
    val image: ImageVector
) {
    MICROPHONE_PERMISSION(
        permission = Manifest.permission.RECORD_AUDIO,
        title = "Microphone Permission Required",
        permanentlyDeclinedText = " Seems you have permanently declined this permission. Give access to this permission in settings",
        rationaleText = "Give access to this permission for Audio Recording.",
        image = Icons.Default.MusicNote
    )

}