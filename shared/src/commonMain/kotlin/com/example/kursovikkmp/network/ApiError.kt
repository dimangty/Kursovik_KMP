package com.example.kursovikkmp.network
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val status: Int?,
    val name: String?,
    val message: String?,
)