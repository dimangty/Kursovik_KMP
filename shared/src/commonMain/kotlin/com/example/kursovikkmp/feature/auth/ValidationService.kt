package com.example.kursovikkmp.feature.auth

interface ValidationService {
    fun isPhoneValid(phone: String): Boolean
    fun isEmailValid(email: String): Boolean
    fun isNameValid(name: String): Boolean
}

class ValidationServiceImpl : ValidationService {
    override fun isPhoneValid(phone: String): Boolean {
        val digitsOnly = phone.filter { it.isDigit() }
        return digitsOnly.length in 7..15
    }


    override fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }

    override fun isNameValid(name: String): Boolean {
        return name.length >= 2
    }
}
