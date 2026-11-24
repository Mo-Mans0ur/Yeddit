package com.example.yeddit.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authRepository: AuthRepository = DemoAuthRepository() // Initializes the authentication repository with a demo implementation
) : ViewModel() {
    // ViewModel class for managing the login UI state and handling login logic

    private val _uiState = MutableStateFlow(LoginUiState()) // Mutable state flow to hold the current UI state of the login screen

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow() // Exposes the UI state as an immutable state flow for observers


    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                errorMessage = null // Clear any previous error message when the email is updated
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                errorMessage = null // Clear any previous error message when the password is updated
            )
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible // Toggle the visibility of the password field
            )
        }
    }

    fun login() {
        val currentState = _uiState.value

        if (currentState.isLoading || currentState.isLoggedIn) {
            return // Prevents multiple login attempts if a login request is already in progress or the user is already logged in
        }

        val validationError = AuthValidator.validateLogin(
            email = currentState.email,
            password = currentState.password
        ) // Validates the email and password inputs using the AuthValidator object, returning an error message if validation fails

        if (validationError != null) {
            _uiState.update {
                it.copy(
                    errorMessage = validationError
                )
            }
            return // If validation fails, update the UI state with the error message and exit the function
        }

        _uiState.update {
            it.copy(
                isLoading = true, // Set the loading state to true to indicate that a login request is in progress
                errorMessage = null // Clear any previous error message when starting a new login request
            )
        }

        viewModelScope.launch {
            try {
                val success = authRepository.login(
                    email = currentState.email.trim(),
                    password = currentState.password.trim()
                ) // Calls the login function of the authentication repository with the trimmed email and password inputs,

                _uiState.update {
                    it.copy(
                        isLoggedIn = success, // Update the UI state to reflect whether the login was successful
                        password = "", // Clear the password field after a login attempt
                        isPasswordVisible = false, // Reset the password visibility to hidden after a login attempt
                        errorMessage = if (success){
                            null
                        } else {
                            "Invalid email or password"
                        } // Set an error message if the login attempt was unsuccessful
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Unable to log in. Please try again later." // Set a generic error message if an exception occurs during the login process
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(
                        isLoading = false // Set the loading state to false, to indicate that the login request has completed
                    )
                }
            }
        }
    }
}
