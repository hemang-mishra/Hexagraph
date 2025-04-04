package com.hexagraph.pattagobhi.model

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
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
    POST_NOTIFICATION(
        permission = Manifest.permission.POST_NOTIFICATIONS,
        title = "Notification Permission Required",
        permanentlyDeclinedText = "Seems you have permanently declined this permission. Give access to this permission if you want to use view notifications.",
        rationaleText = " Give access to this permission if you want to use view notifications.",
        image = Icons.Default.Notifications
    ),
    READ_CALENDAR(
        permission = Manifest.permission.READ_CALENDAR,
        title = "Calendar Read Permission Required",
        permanentlyDeclinedText = "Seems you have permanently declined this permission. Give access to this permission if you want to use view calendar.",
        rationaleText = " Give access to this permission if you want to use view calendar.",
        image = Icons.Default.CalendarToday
    ),
    WRITE_CALENDAR(
        permission = Manifest.permission.WRITE_CALENDAR,
        title = "Calendar Write Permission Required",
        permanentlyDeclinedText = "Seems you have permanently declined this permission. Give access to this permission if you want to use view calendar.",
        rationaleText = " Give access to this permission if you want to use view calendar.",
        image = Icons.Default.EditCalendar
    ),
    FOREGROUND_SERVICE(
        permission = Manifest.permission.FOREGROUND_SERVICE,
        title = "Foreground Service Permission Required",
        permanentlyDeclinedText = "Seems you have permanently declined this permission. Give access to this permission if you want to use view timer.",
        rationaleText = " Give access to this permission if you want to use timer.",
        image = Icons.Default.Timer
    ),
    FOREGROUND_SERVICE_TYPE(
        permission = Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK,
        title = "Foreground Service Type Permission Required",
        permanentlyDeclinedText = "Seems you have permanently declined this permission. Give access to this permission if you want to use view foreground service type.",
        rationaleText = " Give access to this permission if you want to use view foreground service type.",
        image = Icons.Default.MusicNote
    ),

}