package com.example.kursovikkmp.feature.auth

import kotlinx.coroutines.delay

sealed class AuthError : Exception() {
    object InvalidPhone : AuthError()
    object InvalidData : AuthError()
}

data class SignUpData(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val birthDate: String,
    val country: String,
    val city: String,
    val email: String,
    val phone: String
)

interface AuthService {
    suspend fun login(phone: String): Result<Unit>
    suspend fun signUp(data: SignUpData): Result<Unit>
}

class AuthServiceImpl : AuthService {
    override suspend fun login(phone: String): Result<Unit> {
        val digitsOnly = phone.filter { it.isDigit() }

        if (digitsOnly.length < 7 || digitsOnly.length > 15) {
            return Result.failure(AuthError.InvalidPhone)
        }

        delay(400)
        return Result.success(Unit)
    }

    override suspend fun signUp(data: SignUpData): Result<Unit> {
        if (data.firstName.isEmpty() || data.lastName.isEmpty()) {
            return Result.failure(AuthError.InvalidData)
        }

        delay(600)
        return Result.success(Unit)
    }
}
