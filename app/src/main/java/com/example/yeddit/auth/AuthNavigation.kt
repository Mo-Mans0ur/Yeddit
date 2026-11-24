package com.example.yeddit.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yeddit.LoginSuccessScreen

@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
) {
    // Controls navigation between screens
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AuthDestination.Welcome.name,
        modifier = modifier
    ) {
        // Welcome screen
        composable(
            route = AuthDestination.Welcome.name
        ) {
            WelcomeScreen(
                onSignInClick = {
                    navController.navigate(AuthDestination.Login.name)
                },
                onCreateAccountClick = {
                    navController.navigate(AuthDestination.Register.name)
                }
            )
        }

        // Login screen
        composable(
            route = AuthDestination.Login.name
        ) {
            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = loginViewModel,

                onLoginSuccess = {
                    navController.navigate(
                        AuthDestination.Home.name
                    ) {
                        popUpTo(
                            AuthDestination.Welcome.name
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },

                onForgotPasswordClick = {
                    navController.navigate(
                        AuthDestination.ForgotPassword.name
                    )
                },
                onCreateAccountClick = {
                    navController.navigate(
                        AuthDestination.Register.name
                    )
                }
            )
        }

        // Register screen
        composable(
            route = AuthDestination.Register.name
        ) {
            val registerViewModel: RegisterViewModel = viewModel()

            RegisterScreen(
                viewModel = registerViewModel,

                onRegisterSuccess = {
                    navController.navigate(
                        AuthDestination.Login.name
                    ) {
                        popUpTo(
                            AuthDestination.Welcome.name
                        ) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                },

                onSignInClick = {
                    navController.navigate(
                        AuthDestination.Login.name
                    ) {
                        popUpTo(
                            AuthDestination.Welcome.name
                        ) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Forgot password screen
        composable(
            route = AuthDestination.ForgotPassword.name
        ) {
            val forgotPasswordViewModel:
                    ForgotPasswordViewModel = viewModel()

            ForgotPasswordScreen(
                viewModel = forgotPasswordViewModel,

                onBackClick = {
                    navController.popBackStack()
                },

                onSignInClick = {
                    navController.popBackStack(

                    )
                }
            )
        }

        // Temporary home screen after successful login
        composable(
            route = AuthDestination.Home.name
        ) {


            LoginSuccessScreen()
        }
    }
}