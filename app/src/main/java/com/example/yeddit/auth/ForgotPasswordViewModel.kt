package com.example.yeddit.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ForgotPasswordViewModel (
    private val authRepository: AuthRepository = DemoAuthRepository()
    ) : ViewModel() {
    // Holds the current password recovery state
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())


    // Exposes the state as a read-only StateFlow for observers
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    // Updates the email in the UI state
    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                errorMessage = null, // Clear any previous error message when the email is updated
                isResetRequested = false // Reset the reset request status when the email is updated
            )
        }
    }

    // Handles password reset request
    fun requestPasswordReset() {
        val currentState = _uiState.value

        // Prevents duplicate requests
        if (currentState.isLoading || currentState.isResetRequested) {
            return
        }

        // Validate the email input
        val validationError = AuthValidator.validateEmail(
            currentState.email
        )

        // Stops the request if validation fails
        if (validationError != null) {
            _uiState.update {
                it.copy(
                    errorMessage = validationError
                )
            }
            return
        }

        // Starts the loading state
        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        // Performs the password reset request
        viewModelScope.launch {
            try {
                val succes = authRepository.requestPasswordReset(
                    email = currentState.email.trim()
                )

                // update the result
                _uiState.update {
                    it.copy(
                        isResetRequested = succes,
                        errorMessage = if (succes) {
                            null // Clear any previous error message if the request was successful
                        } else {
                            "Failed to send password reset email. Please try again." // Set an error message if the request failed
                        }
                    )
                }
            } catch (exception: CancellationException) {
                // Handle cancellation exception if needed
                throw exception
            } catch (exception: Exception) {
                // Handle other exceptions if needed
                _uiState.update {
                    it.copy(
                        errorMessage = "An unexpected error occurred. Please try again."
                    )
                }
            } finally {
                // Reset the loading state after the request is completed
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}
