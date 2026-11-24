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
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Yeddit",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Email") },
            singleLine = true,
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the email input field when a login request is in progress or the user is already logged in
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), // Set the keyboard type to Email for the email input field

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            label = { Text("Password") },
            singleLine = true,
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the password input field when a login request is in progress or the user is already logged in
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
            modifier = Modifier.fillMaxWidth()
        )

        uiState.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = viewModel::login,
            enabled = !uiState.isLoading && !uiState.isLoggedIn, // Disable the login button when a login request is in progress or the user is already logged in
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Login")
            }
        }

    }

}