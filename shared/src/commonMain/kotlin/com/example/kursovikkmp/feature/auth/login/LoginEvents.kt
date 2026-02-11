package com.example.kursovikkmp.feature.auth.login

import com.example.kursovikkmp.base.BaseEvent

sealed class LoginEvents : BaseEvent {
    data class PhoneChanged(val phone: String) : LoginEvents()
    object LoginButtonTapped : LoginEvents()
    object SignUpButtonTapped : LoginEvents()
}
