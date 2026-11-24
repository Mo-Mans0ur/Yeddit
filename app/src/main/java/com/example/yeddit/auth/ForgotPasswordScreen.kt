package com.example.yeddit.auth

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    //observes the forgot password UI state
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Back button
        TextButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Back")
        }

        Spacer(modifier = Modifier.height(64.dp))

        // Screen header
        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password recovery instructions
        Text(
            text = "Enter your School Mail to request a password reset link.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // School email input field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            label = {
                Text("School Mail")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth() // Disable the email input field when a forgot password request is in progress
        )

        // Displays validation or request errors
        uiState.errorMessage?.let { errorMessage ->

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Reset a password reset
        Button(
            onClick = viewModel::requestPasswordReset,
            enabled = !uiState.isLoading &&
                    !uiState.isResetRequested, // Disable the button when a forgot password request is in progress or the reset email has already been sent
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {

            if (uiState.isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                )
            } else {
                Text("Send Reset Link")
            }
        }

        if (uiState.isResetRequested) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A password reset link has been sent to your email." +
                " this is just a demo no email will be sent.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Returns to the login screen
        TextButton(
            onClick = onSignInClick,
            enabled = !uiState.isLoading

        ) {
            Text("Back to sign In")
        }
    }

}