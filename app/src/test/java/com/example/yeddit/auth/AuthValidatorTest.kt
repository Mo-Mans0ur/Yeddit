package com.example.yeddit.auth

import org.junit.Test // the framework responsible for running the unit tests

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

class AuthValidatorTest {

    // Test whether an empty email is rejected
    @Test // identifies a function as a unit test
    fun emptyEmailIsRejected() {

        val result = AuthValidator.validateEmail("")

        assertEquals("Email is required", result) // checks if the result matches the expected error message

    }

    // Test whether an invalid email format is rejected
    @Test
    fun invalidEmailFormatIsRejected() {

        val result = AuthValidator.validateEmail("student")

        assertEquals("Invalid email address", result)

    }

    // Test whether a non-yeditepe email is rejected
    @Test
    fun nonStudentEmailIsRejected() {

        val result = AuthValidator.validateEmail(
            "student@gmail.com"
        )

        assertEquals(
            "Please use your Yeditepe student email",
            result
        )

    }

    // Test whether a valid school email is accepted
    @Test
    fun validStudentEmailIsAccepted() {

        val result = AuthValidator.validateEmail("student@std.yeditepe.edu.tr")

        assertNull(result) // Checks whether the result is null

    }

    // Test whether uppercase email domains are accepted
    @Test
    fun uppercaseStudentEmailIsAccepted() {

        val result = AuthValidator.validateEmail("student@STD.YEDITEPE.EDU.TR")

        assertNull(result)

    }

    // Test whether an empty password is rejected
    @Test
    fun emptyPasswordIsRejected() {

        val result = AuthValidator.validatePassword("")

        assertEquals("Password is required", result)

    }

    // Test whether a valid password is accepted
    @Test
    fun validPasswordIsAccepted() {

        val result = AuthValidator.validatePassword("test123")

        assertNull(result)
    }

    // Test whether an empty full name is rejected
    @Test
    fun emptyFullNameIsRejected() {
        val result = AuthValidator.validateFullName("")

        assertEquals("Full name is required", result)
    }


    //Test whether valid registration credentials are accepted
    @Test
    fun validRegistrationIsAccepted() {
        val result = AuthValidator.validateRegister(
            fullName = "Test Student",
            email = "student@std.yeditepe.edu.tr",
            password = "test123"
        )

        assertNull(result)
    }
}