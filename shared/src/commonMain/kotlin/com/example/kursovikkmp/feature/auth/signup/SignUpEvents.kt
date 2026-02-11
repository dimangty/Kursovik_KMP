package com.example.kursovikkmp.feature.auth.signup

import com.example.kursovikkmp.base.BaseEvent

sealed class SignUpEvents : BaseEvent {
    data class FirstNameChanged(val value: String) : SignUpEvents()
    data class LastNameChanged(val value: String) : SignUpEvents()
    data class GenderChanged(val value: String) : SignUpEvents()
    data class BirthDateChanged(val value: String) : SignUpEvents()
    data class CountryChanged(val value: String) : SignUpEvents()
    data class CityChanged(val value: String) : SignUpEvents()
    data class EmailChanged(val value: String) : SignUpEvents()
    data class PhoneChanged(val value: String) : SignUpEvents()
    object CreateAccountTapped : SignUpEvents()
    object BackButtonTapped : SignUpEvents()
}
