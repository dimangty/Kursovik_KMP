package com.example.kursovikkmp.DB

import com.example.kursovikkmp.Database
import com.example.kursovikkmp.database.ProfileDb
import com.example.kursovikkmp.feature.profile.ProfileData

class ProfileDao(
    private val db: Database
) {
    private val profileQueries = db.profileDbQueries

    fun get(): ProfileDb? = runCatching {
        profileQueries.get().executeAsOneOrNull()
    }.getOrNull()

    suspend fun insert(data: ProfileData) {
        runCatching {
            profileQueries.insert(
                firstName = data.firstName,
                lastName = data.lastName,
                gender = data.gender,
                birthDate = data.birthDate,
                country = data.country,
                city = data.city,
                email = data.email,
                phone = data.phone,
                photoPath = data.photoPath
            )
        }
    }

    suspend fun updatePhoto(photoPath: String) {
        runCatching {
            profileQueries.updatePhoto(photoPath = photoPath)
        }
    }

    suspend fun clear() {
        runCatching {
            profileQueries.clear()
        }
    }
}
