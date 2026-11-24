package com.example.yeddit.auth

class DemoAuthRepository : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Boolean {
        val demoEmail = "student@example.com" // Represents a hardcoded demo email address for testing purposes
        val demoPassword = "yeddit123" // Represents a hardcoded demo password for testing purposes

        return email.trim().equals(
            demoEmail,
            ignoreCase = true

        ) && password.trim() == demoPassword // Compares the trimmed input email and password with the hardcoded demo credentials, returning true if they match, otherwise false
    }
}