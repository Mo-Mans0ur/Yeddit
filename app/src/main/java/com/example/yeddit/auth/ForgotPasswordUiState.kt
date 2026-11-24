package com.example.yeddit.auth

data class ForgotPasswordUiState(
    val email: String = "", // Represents the email input field value
    val isLoading: Boolean = false, // Represents whether a forgot password request is in progress
    val isResetRequested: Boolean = false, // Represents whether the password reset email has been sent
    val errorMessage: String? = null // Represents any error message to be displayed
)
