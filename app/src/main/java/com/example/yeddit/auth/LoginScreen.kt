package com.example.yeddit.auth

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //observes the login UI state
    val uiState by viewModel.uiState.collectAsState()

    // Navigates after successful login
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess() // Call the onLoginSuccess callback when the user is logged in
        }
    }

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
            modifier = Modifier.align(Alignment.Start),
            enabled = !uiState.isLoading && !uiState.isLoggedIn
        ) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Welcome back to your school community",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email input field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            label = {
                Text("Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), // Set the keyboard type to Email for the email input field
            singleLine = true,
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the email input field when a login request is in progress or the user is already logged in
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            label = {
                Text("Password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ), // Set the keyboard type to Password for the password input field
            visualTransformation = if (uiState.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }, // Toggle the visibility of the password field based on the isPasswordVisible state

            trailingIcon = {
                TextButton(
                    onClick = viewModel::togglePasswordVisibility,
                    enabled = !uiState.isLoading && !uiState.isLoggedIn // Disable the toggle button when a login request is in progress or the user is already logged in
                ) {
                    Text(
                        text = if (uiState.isPasswordVisible) {
                            "Hide"
                        } else {
                            "Show"
                        }
                    )
                }
            },
            singleLine = true,
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the password input field when a login request is in progress or the user is already logged in
            modifier = Modifier.fillMaxWidth()
        )

        // Navigates to the password recovery screen
        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End),
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the button when a login request is in progress or the user is already logged in
        ){
            Text("Forgot Password?")
        }

        // Displays validation or login errors
        uiState.errorMessage?.let { errorMessage ->

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login button
        Button(
            onClick = viewModel::login,
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the login button when a login request is in progress or the user is already logged in
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Sign In")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Navigates to the registration screen
        TextButton(
            onClick = onCreateAccountClick,
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the button when a login request is in progress or the user is already logged in
        ) {
            Text("Create Account")
        }
    }

}