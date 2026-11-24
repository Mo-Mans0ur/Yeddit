package com.example.yeddit.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository = DemoAuthRepository()
    ) : ViewModel() {

    // Holds the current state of the registration UI
    private val _uiState = MutableStateFlow(RegisterUiState())

    // Exposes the state as a read-only StateFlow for observers
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    // Updates the full name in the UI state
    fun updateFullName(fullName: String) {
        _uiState.update {
            it.copy(
                fullName = fullName,
                errorMessage = null // Clear any previous error message when the full name is updated
            )
        }
    }

    // Updates the email in the UI state
    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                errorMessage = null // Clear any previous error message when the email is updated
            )
        }
    }

    // Updates the password in the UI state
    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                errorMessage = null // Clear any previous error message when the password is updated
            )
        }
    }


    // Toggles the visibility of the password in the UI state
    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible // Toggle the visibility of the password field
            )
        }
    }


    // handles account registration
    fun register() {

        val currentState = _uiState.value

        if (currentState.isLoading || currentState.isRegistered) {
            return // Prevents multiple registration attempts if a registration request is already in progress or the user is already registered
        }

        // Validates the registration fields
        val validationError = AuthValidator.validateRegister(
            fullName = currentState.fullName,
            email = currentState.email,
            password = currentState.password
        )

        // Stops registration if validation fails
        if (validationError != null) {
            _uiState.update {
                it.copy(
                    errorMessage = validationError
                )
            }
            return
        }

        // Start the loading state
        _uiState.update {
            it.copy(
                isLoading = true, // Set the loading state to true to indicate that a registration request is in progress
                errorMessage = null // Clear any previous error message when starting a new registration request
            )
        }

        // Perform the registration using coroutine
        viewModelScope.launch {
            try {
                val success = authRepository.register(
                    fullName = currentState.fullName.trim(),
                    email = currentState.email.trim(),
                    password = currentState.password
                )
                // Updates the registration result
                _uiState.update {
                    it.copy(
                        isRegistered = success,
                        password = if (success) "" else it.password, // Clear the password field if registration is successful
                        isPasswordVisible = false,
                        errorMessage = if (success) {
                            null
                        } else {
                            "An account with this email already exists. Please try logging in instead."
                        }
                    )
                }
            } catch (exception: CancellationException){
                throw exception
            } catch (exception: Exception) {
                // Handle any unexpected errors during registration
                _uiState.update {
                    it.copy(
                        errorMessage = "An unexpected error occurred. Please try again later."
                    )
                }
            } finally {
                // Reset the loading state after the registration attempt
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}