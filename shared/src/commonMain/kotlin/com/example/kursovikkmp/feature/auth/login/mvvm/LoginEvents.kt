package com.example.kursovikkmp.feature.login.mvvm

import com.example.kursovikkmp.base.BaseEvent

sealed class LoginUIEvent : BaseEvent {
    data object OnLoginButtonClicked : LoginUIEvent()
    data object OnSignUpButtonClicked : LoginUIEvent()

    class OnUsernameFieldFocusChanged(val focused: Boolean) : LoginUIEvent()
    class OnPhoneNumberFieldFocusChanged(val focused: Boolean) : LoginUIEvent()
    class OnPasswordFieldFocusChanged(val focused: Boolean) : LoginUIEvent()
    class OnUsernameFieldValueChange(val value: String) : LoginUIEvent()
    class OnPhoneNumberValueChange(val value: String) : LoginUIEvent()
    class OnPasswordFieldValueChange(val value: String) : LoginUIEvent()
}