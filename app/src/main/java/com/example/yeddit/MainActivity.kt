package com.example.yeddit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.yeddit.auth.LoginScreen
import com.example.yeddit.auth.LoginViewModel
import com.example.yeddit.ui.theme.YedditTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val loginViewModel = ViewModelProvider(this)[
            LoginViewModel::class.java
        ]

        setContent {
            YedditTheme {

                var isLoggedIn by rememberSaveable {
                    mutableStateOf(false)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()

                ) { innerPadding ->

                    if (isLoggedIn) {
                        LoginSuccessScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        LoginScreen(
                            viewModel = loginViewModel,
                            onLoginSuccess = {
                                isLoggedIn = true
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun LoginSuccessScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Login Successful!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome to Yeddit!",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}