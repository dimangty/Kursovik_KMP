package com.example.kursovikkmp.feature.profile

import com.example.kursovikkmp.base.BaseEvent

sealed class ProfileEvents : BaseEvent {
    data class PhotoChanged(val photoPath: String) : ProfileEvents()
    data object LogoutTapped : ProfileEvents()
}
