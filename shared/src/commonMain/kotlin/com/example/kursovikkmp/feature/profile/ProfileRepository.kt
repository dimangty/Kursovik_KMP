package com.example.kursovikkmp.feature.profile

import com.example.kursovikkmp.DB.ProfileDao

interface ProfileRepository {
    fun hasSavedProfile(): Boolean
    fun getProfileOrMock(): ProfileData
    suspend fun saveProfile(data: ProfileData)
    suspend fun updatePhoto(photoPath: String)
    suspend fun clear()
}

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao
) : ProfileRepository {
    override fun hasSavedProfile(): Boolean = profileDao.get() != null

    override fun getProfileOrMock(): ProfileData {
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

    override suspend fun saveProfile(data: ProfileData) {
        profileDao.insert(data)
    }

    override suspend fun updatePhoto(photoPath: String) {
        if (hasSavedProfile()) {
            profileDao.updatePhoto(photoPath)
        } else {
            val fallback = ProfileData.mock().copy(photoPath = photoPath)
            profileDao.insert(fallback)
        }
    }

    override suspend fun clear() {
        profileDao.clear()
    }
}
