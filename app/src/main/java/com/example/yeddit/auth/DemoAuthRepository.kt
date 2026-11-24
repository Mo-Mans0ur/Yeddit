package com.example.yeddit.auth

class DemoAuthRepository : AuthRepository {

    private data class DemoUser(
        val fullName: String,
        val email: String,
        val password: String
    )

    companion object {
        // Shared temporary storage for demo accounts
        private val users = mutableListOf(
            DemoUser(
                fullName = "Demo Student",
                email = "demo@std.yeditepe.edu.tr",
                password = "yeddit123"
            )
        )
    }

    // Authenticate an existing user
    override suspend fun login(
        email: String,
        password: String
    ): Boolean {

        return users.any { user ->
            user.email.equals(
                email,
                ignoreCase = true
            ) && user.password == password
        }
    }


    // Register a new user
    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean {
        // Check if the email is already registered

        val trimmedEmail = email.trim()

        val userExists = users.any { user ->
            user.email.equals(
                trimmedEmail,
                ignoreCase = true
            )
        }

        if (userExists) {
            return false // Registration failed, email already exists
        }

        // Add the new user to the list
        val newUser = DemoUser(
            fullName = fullName,
            email = trimmedEmail,
            password = password
        )

        // Add the new user to the list
        users.add(newUser)

        return true // Registration successful
    }

    // Request a password reset for a user
    override suspend fun requestPasswordReset(
        email: String
    ): Boolean {

        val validationError = AuthValidator.validateEmail(email)
        if (validationError != null) {
            return false // Invalid email format
        }
        // Simulates an accepted password reset request for demo purposes
        // no actual email is sent in this demo implementation
        return true
    }
}