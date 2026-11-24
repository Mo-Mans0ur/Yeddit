package com.example.yeddit.auth

data class RegisterUiState(
    val fullName: String = "", // Represents the full name input field value
    val email: String = "", // Represents the email input field value
    val password: String = "", // Represents the password input field value
    //val confirmPassword: String = "", // Represents the confirmation password input field value
    val isPasswordVisible: Boolean = false, // Represents whether the password is visible or hidden
    val isLoading: Boolean = false, // Represents whether a registration request is in progress
    val isRegistered: Boolean = false, // Represents whether the user is registered
    val errorMessage: String? = null // Represents any error message to be displayed
)
