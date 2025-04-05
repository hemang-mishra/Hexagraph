package com.hexagraph.pattagobhi.ui.screens.onboarding

import androidx.compose.runtime.toMutableStateList
import com.hexagraph.pattagobhi.model.PermissionsRequired

object OnboardingHelper {
    var isNavigationDone = false
    val visiblePermissionDialogQueue = PermissionsRequired.entries.toMutableStateList()

    fun dismissDialogue(permission: PermissionsRequired){
        visiblePermissionDialogQueue.remove(permission)
    }

    fun onPermissionInteractionResult(
        permissionsRequired: PermissionsRequired,
        isGranted: Boolean
    ){
        if(!isGranted){
            visiblePermissionDialogQueue.add(permissionsRequired)
        }else{
            visiblePermissionDialogQueue.remove(permissionsRequired)
        }
    }

    fun areAllPermissionsGranted() = (visiblePermissionDialogQueue.isEmpty())

}