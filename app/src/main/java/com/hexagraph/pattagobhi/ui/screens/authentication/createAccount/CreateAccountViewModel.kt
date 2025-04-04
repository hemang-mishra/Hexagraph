package com.hexagraph.pattagobhi.ui.screens.authentication.createAccount

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hexagraph.pattagobhi.service.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(val auth: FirebaseAuth = FirebaseAuth.getInstance(), private val service: AuthenticationService) : ViewModel() {
    var uiState = mutableStateOf(CreateAccountUIState())
        private set

    fun onEmailChange(newEmail: String) {
        uiState.value = uiState.value.copy(email = newEmail)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        uiState.value = uiState.value.copy(confirmPassword = newConfirmPassword)
    }

    fun onEmailErrorStateChange(isError: Boolean, errorText: String) {
        uiState.value = uiState.value.copy(isEmailValid = !isError, emailError = errorText)
    }

    fun onPasswordErrorStateChange(isError: Boolean, errorText: String) {
        uiState.value = uiState.value.copy(isPasswordValid = !isError, passwordError = errorText)
    }

    fun onConfirmPasswordErrorStateChange(isError: Boolean, errorText: String) {
        uiState.value =
            uiState.value.copy(isConfirmPasswordValid = !isError, confirmPasswordError = errorText)
    }

    fun onPasswordChange(newPassword: String) {
        uiState.value = uiState.value.copy(password = newPassword)
    }

    fun onNameChange(newName: String) {
        uiState.value = uiState.value.copy(name = newName)
    }

    fun onNameErrorStateChange(isError: Boolean, errorText: String) {
        uiState.value = uiState.value.copy(isNameValid = !isError, nameError = errorText)
    }

    fun showSnackbar(snackbarHostState: SnackbarHostState, message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    private fun addNameInAccount(onSuccess: () -> Unit, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            service.updateUserProfile(auth, uiState.value.name) { task ->
                uiState.value = uiState.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    onSuccess()
                } else if (task.exception != null) {
                    showSnackbar(
                        snackbarHostState,
                        task.exception!!.message ?: "Failed to add name in account"
                    )
                    Log.e("CreateAccountViewModel", "Failed to add name in account", task.exception)
                } else {
                    showSnackbar(snackbarHostState, "Failed to add name in account")
                    Log.e("CreateAccountViewModel", "Failed to add name in account")
                }
            }
        }
    }

    fun createAccount(snackbarHostState: SnackbarHostState, onSuccess: () -> Unit) {
        if (uiState.value.isLoading)
            return
        uiState.value = uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            service.createAccountWithEmail(
                auth,
                uiState.value.email,
                uiState.value.password,
                uiState.value.name
            ) { task ->
                if (task.isSuccessful) {
                    addNameInAccount(onSuccess, snackbarHostState)
                } else if (task.exception != null) {
                    uiState.value = uiState.value.copy(isLoading = false)
                    showSnackbar(
                        snackbarHostState,
                        task.exception!!.message ?: "Failed to create account"
                    )
                    Log.e("CreateAccountViewModel", "Failed to create account", task.exception)
                } else {
                    uiState.value = uiState.value.copy(isLoading = false)
                    showSnackbar(snackbarHostState, "Failed to create account")
                    Log.e("CreateAccountViewModel", "Failed to create account")

                }
            }
        }
    }
}
