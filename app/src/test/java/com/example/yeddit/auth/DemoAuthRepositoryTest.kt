package com.example.yeddit.auth

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.UUID

class DemoAuthRepositoryTest {

    private val repository = DemoAuthRepository()

    // Test login with valid credentials
    @Test
    fun correctCredentialsAreAccepted() = runBlocking {
        val result = repository.login(
            "demo@std.yeditepe.edu.tr",
            "yeddit123"
        )

        assertTrue(result) // Checks if the result is true
    }

    // Test login with invalid credentials
    @Test
    fun incorrectPasswordIsRejected() = runBlocking {
        val result = repository.login(
            "demo@std.yeditepe.edu.tr",
            "wrongpassword"
        )

        assertFalse(result) // Checks if the result is false
    }

    // Tests login with unknown account
    @Test
    fun unknownAccountIsRejected() = runBlocking {
        val result = repository.login(
            "unknown@std.yeditepe.edu.tr",
            "test123"
        )

        assertFalse(result) // Checks if the result is false
    }

    // Tests whether uppercase email addresses are accepted
    @Test
    fun uppercaseEmailIsAccepted() = runBlocking {
        val result = repository.login(
            "DEMO@STD.YEDITEPE.EDU.TR",
            "yeddit123"
        )

        assertTrue(result) // Checks if the result is true
    }

    // Test registration with a new account
    @Test
    fun newAccountCanBeRegistered() = runBlocking {

        val email =
            "${UUID.randomUUID()}@std.yeditepe.edu.tr" // Generates a unique email address for testing

        val result = repository.register(
            "New Student",
            email = email,
            password = "test123"
        )

        assertTrue(result) // Checks if the result is true
    }


    // Tests whether duplicate account is rejected
    @Test
    fun duplicateAccountIsRejected() = runBlocking {

        val email = "${UUID.randomUUID()}@std.yeditepe.edu.tr" // Generates a unique email address for testing

        val firstRegistration = repository.register(
            "Duplicate Student",
            email = email,
            password = "test123"
        )

        assertTrue(firstRegistration) // Checks if the result is true

        val secondRegistration = repository.register(
            "Duplicate Student",
            email = email,
            password = "test123"
        )

        assertFalse(secondRegistration) // Checks if the result is false
    }

    // Tests login with a newly registered account
    @Test
    fun registeredAccountCanLogin() = runBlocking {
        val email =
            "${UUID.randomUUID()}@std.yeditepe.edu.tr" // Generates a unique email address for testing

        val registrationResult = repository.register(
            "New Student",
            email = email,
            password = "test123"
        )

        assertTrue(registrationResult) // Checks if the registration was successful

        val loginResult = repository.login(
            email = email,
            password = "test123"
        )

        assertTrue(loginResult) // Checks if the login was successful
    }

    // Tests whether a password reset request is successful for an existing account
    @Test
    fun validPasswordResetRequestIsAccepted() = runBlocking {
        val result = repository.requestPasswordReset(
            email = "demo@std.yeditepe.edu.tr"
        )

        assertTrue(result) // Checks if the password reset request was successful
    }

    // Tests password reset request for an invalid email
    @Test
    fun invalidPasswordResetRequestIsRejected() = runBlocking {

        val result = repository.requestPasswordReset(
            email = "student@gmail.com"
        )

        assertFalse(result) // Checks if the password reset request was rejected
    }
}