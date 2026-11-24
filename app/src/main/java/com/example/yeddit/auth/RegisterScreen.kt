package com.example.yeddit.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
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
fun RegisterScreen(
    viewModel: RegisterViewModel, // The ViewModel that manages the state and logic for the registration screen
    onRegisterSuccess: () -> Unit, // A callback function that will be called when the registration is successful
    onSignInClick: () -> Unit, // A callback function that will be called when the "Sign In" button is clicked
    onBackClick: () -> Unit, // A callback function that will be called when the "Back" button is clicked
    modifier: Modifier = Modifier // A modifier that can be used to customize the layout and appearance
) {

    //observes the registration UI state
    val uiState by viewModel.uiState.collectAsState()

    // Navigates after successful registration
    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onRegisterSuccess() // Call the onRegisterSuccess callback when the user is registered
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Back")
        }

        Spacer(modifier = Modifier.padding(32.dp))

        // Registration heading
        Text(
            text = "CREATE AN ACCOUNT",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "JOIN THE CAMPUS",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.padding(32.dp))

        // Full name input field
        OutlinedTextField(
            value = uiState.fullName,
            onValueChange = viewModel::updateFullName,
            label = {
                Text("Full Name")
            },
            singleLine = true,
            enabled = !uiState.isLoading && !uiState.isRegistered,
            modifier = Modifier.fillMaxWidth() // Disable the full name input field when a registration request is in progress or the user is already registered
        )


        Spacer(modifier = Modifier.padding(16.dp))

        // School email input field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            label = {
                Text("School Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            enabled = !uiState.isLoading && !uiState.isRegistered, // Disable the email input field when a registration request is in progress or the user is already registered
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // Password input field
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            label = {
                Text("Password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (uiState.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = viewModel::togglePasswordVisibility,
                    enabled = !uiState.isLoading && !uiState.isRegistered // Disable the toggle button when a registration request is in progress or the user is already registered
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
            enabled = !uiState.isLoading && !uiState.isRegistered, // Disable the password input field when a registration request is in progress or the user is already registered
            modifier = Modifier.fillMaxWidth()
        )

        // Display validation or registration error
        uiState.errorMessage?.let { errorMessage ->

            Spacer(modifier = Modifier.padding(12.dp))

            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.padding(24.dp))

        // Registration button
        Button(
            onClick = viewModel::register,
            enabled = !uiState.isLoading && !uiState.isRegistered, // Disable the registration button when a registration request is in progress or the user is already registered
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ){
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                )
            } else {
                Text("Create")
            }
        }

        Spacer(modifier = Modifier.padding(24.dp))

        // Navigate to log in screen
        TextButton(
            onClick = onSignInClick,
            enabled = !uiState.isLoading
        ) {
            Text("Already have an account? Sign In")
        }
    }
}