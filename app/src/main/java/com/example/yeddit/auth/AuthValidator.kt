package com.example.yeddit.auth

object AuthValidator {

    fun validateEmail(email: String): String? {
        if (email.isBlank()) {
            return "Email is required"
        }

        val emailPattern = Regex(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        )

        if (!emailPattern.matches(email.trim())) {
            return "Invalid email address"
        }
        return null
    }

    fun validatePassword(password: String): String? {
        if (password.isBlank()) {
            return "Password is required"
        }
        return null
    }

    fun validateLogin(
        email: String,
        password: String
    ): String? {
        return validateEmail(email)
            ?: validatePassword(password)
    }
}