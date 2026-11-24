package com.example.yeddit.auth

data class LoginUiState(
    val email: String = "", // Represents the email input field value
    val password: String = "", // Represents the password input field value
    val isPasswordVisible: Boolean = false, // Represents whether the password is visible or hidden
    val isLoading: Boolean = false, // Represents whether a login request is in progress
    val isLoggedIn: Boolean = false, // Represents whether the user is logged in
    val errorMessage: String? = null // Represents any error message to be displayed
)
