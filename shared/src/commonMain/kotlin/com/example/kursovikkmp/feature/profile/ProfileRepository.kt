package com.example.kursovikkmp.feature.profile

import com.example.kursovikkmp.DB.ProfileDao

class ProfileRepository(
    private val profileDao: ProfileDao
) {
    fun hasSavedProfile(): Boolean = profileDao.get() != null

    fun getProfileOrMock(): ProfileData {
        return profileDao.get()?.let {
            ProfileData(
                firstName = it.firstName,
                lastName = it.lastName,
                gender = it.gender,
                birthDate = it.birthDate,
                country = it.country,
                city = it.city,
                email = it.email,
                phone = it.phone,
                photoPath = it.photoPath
            )
        } ?: ProfileData.mock()
    }

    suspend fun saveProfile(data: ProfileData) {
        profileDao.insert(data)
    }

    suspend fun updatePhoto(photoPath: String) {
        if (hasSavedProfile()) {
            profileDao.updatePhoto(photoPath)
        } else {
            val fallback = ProfileData.mock().copy(photoPath = photoPath)
            profileDao.insert(fallback)
        }
    }

    suspend fun clear() {
        profileDao.clear()
    }
}
