package com.example.yeddit.auth

interface AuthRepository {

    // Authenticate an existing user
    suspend fun login( // Allows the function to suspend during asynchronous operations, such as network requests
        email: String, // Receives the user's email as a parameter
        password: String // Receives the user's password as a parameter
    ): Boolean // returns a Boolean indicating whether the login was successful (true) or not (false)

    // Register a new user
    suspend fun register( // Allows the function to suspend during asynchronous operations, such as network requests
        fullName: String, // Receives the user's full name as a parameter
        email: String, // Receives the user's email as a parameter
        password: String // Receives the user's password as a parameter
    ): Boolean // returns a Boolean indicating whether the registration was successful (true) or not (false)


    // Request a password reset for a user
    suspend fun requestPasswordReset( // Allows the function to suspend during asynchronous operations, such as network requests
        email: String // Receives the user's email as a parameter
    ): Boolean // returns a Boolean indicating whether the password reset request was successful (true) or not (false)
}