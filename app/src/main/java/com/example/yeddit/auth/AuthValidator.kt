package com.example.yeddit.auth

object AuthValidator {

    private const val STUDENT_DOMAIN = "@std.yeditepe.edu.tr" // Represents the domain for student email addresses

    // Validates the user's full name
    fun validateFullName(fullName: String): String? {
        if (fullName.isBlank()) {
            return "Full name is required"
        }
        return null
    }

    // Validates the user's School email address
    fun validateEmail(email: String): String? {

        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()) {
            return "Email is required"
        }

        val emailPattern = Regex(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        )

        if (!emailPattern.matches(trimmedEmail)) {
            return "Invalid email address"
        }

        if (!trimmedEmail.endsWith(
                STUDENT_DOMAIN,
                ignoreCase = true
        )
            ) {
            return "Please use your Yeditepe student email"
        }

        return null
    }

    // Validates the user's password
    fun validatePassword(password: String): String? {
        if (password.isBlank()) {
            return "Password is required"
        }
        return null
    }

    // Validates the user's login credentials (email and password)
    fun validateLogin(
        email: String,
        password: String
    ): String? {
        return validateEmail(email)
            ?: validatePassword(password)
    }

    fun validateRegister(
        fullName: String,
        email: String,
        password: String
    ): String? {
        return validateFullName(fullName)
            ?: validateEmail(email)
            ?: validatePassword(password)
    }
}