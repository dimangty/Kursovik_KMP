package com.example.kursovikkmp.feature.profile

data class ProfileData(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val birthDate: String,
    val country: String,
    val city: String,
    val email: String,
    val phone: String,
    val photoPath: String
) {
    companion object {
        fun mock() = ProfileData(
            firstName = "John",
            lastName = "Doe",
            gender = "Male",
            birthDate = "1994-05-11",
            country = "USA",
            city = "New York",
            email = "john.doe@example.com",
            phone = "+11234567890",
            photoPath = ""
        )
    }
}
