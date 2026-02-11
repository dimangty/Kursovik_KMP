package com.example.kursovikkmp.feature.auth

import com.example.kursovikkmp.feature.profile.ProfileData
import com.example.kursovikkmp.feature.profile.ProfileRepository
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
    suspend fun verifyPin(pin: String): Result<Unit>
    suspend fun logout()
}

class AuthServiceImpl(
    private val profileRepository: ProfileRepository
) : AuthService {
    private companion object {
        const val MOCK_PIN = "555555"
    }

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
        profileRepository.saveProfile(
            ProfileData(
                firstName = data.firstName,
                lastName = data.lastName,
                gender = data.gender,
                birthDate = data.birthDate,
                country = data.country,
                city = data.city,
                email = data.email,
                phone = data.phone,
                photoPath = ""
            )
        )
        return Result.success(Unit)
    }

    override suspend fun verifyPin(pin: String): Result<Unit> {
        delay(700)
        return if (pin == MOCK_PIN) {
            Result.success(Unit)
        } else {
            Result.failure(AuthError.InvalidData)
        }
    }

    override suspend fun logout() {
        profileRepository.clear()
    }
}
