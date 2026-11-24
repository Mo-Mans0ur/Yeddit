package com.example.yeddit.auth

interface AuthRepository {

    suspend fun login( // Allows the function to suspend during asynchronous operations, such as network requests
        email: String, // Receives the user's email as a parameter
        password: String // Receives the user's password as a parameter
    ): Boolean // returns a Boolean indicating whether the login was successful (true) or not (false)
}