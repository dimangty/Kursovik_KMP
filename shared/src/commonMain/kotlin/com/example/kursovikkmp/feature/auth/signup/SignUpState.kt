package com.example.kursovikkmp.feature.auth.signup

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.DropdownFieldState
import com.example.kursovikkmp.common.view.TextFieldState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.getMock
import com.example.kursovikkmp.common.view.updateEnabled
import com.example.kursovikkmp.common.view.updateValue

data class SignUpState(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val country: String = "",
    val city: String = "",
    val email: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    override val titleBarState: TitleBarState = TitleBarState.getMock()
) : BaseViewState {
    val genderOptions = listOf("Male", "Female", "Other")
    val countryOptions = listOf("USA", "UK", "Germany", "France")
    val cityOptions = listOf("New York", "London", "Berlin", "Paris")

    val isFormValid: Boolean
        get() = firstName.length >= 2
                && lastName.length >= 2
                && gender.isNotEmpty()
                && country.isNotEmpty()
                && city.isNotEmpty()
                && email.isNotEmpty()
                && phone.isNotEmpty()

    // UI Components configured from shared layer
    val firstNameField: TextFieldState = TextFieldState(
        value = firstName,
        placeholder = MR.strings.first_name,
        keyboardType = TextFieldState.KeyboardType.Text
    )

    val lastNameField: TextFieldState = TextFieldState(
        value = lastName,
        placeholder = MR.strings.last_name,
        keyboardType = TextFieldState.KeyboardType.Text
    )

    val genderField: DropdownFieldState = DropdownFieldState(
        value = gender,
        placeholder = MR.strings.gender,
        options = genderOptions
    )

    val birthDateField: TextFieldState = TextFieldState(
        value = birthDate,
        placeholder = MR.strings.birth_date,
        keyboardType = TextFieldState.KeyboardType.Text
    )

    val countryField: DropdownFieldState = DropdownFieldState(
        value = country,
        placeholder = MR.strings.country,
        options = countryOptions
    )

    val cityField: DropdownFieldState = DropdownFieldState(
        value = city,
        placeholder = MR.strings.city,
        options = cityOptions
    )

    val emailField: TextFieldState = TextFieldState(
        value = email,
        placeholder = MR.strings.email,
        keyboardType = TextFieldState.KeyboardType.Email
    )

    val phoneField: TextFieldState = TextFieldState(
        value = phone,
        placeholder = MR.strings.phone,
        keyboardType = TextFieldState.KeyboardType.Phone
    )

    val createAccountButton: ButtonState = ButtonState.primary(
        value = "Create Account", // Will be set by ViewModel
        background = if (isFormValid) MR.colors.primary else MR.colors.grey
    ).updateEnabled(isFormValid && !isLoading)

    val errorTextState: TextState? = errorMessage?.let {
        TextState.latoRegular(12, MR.colors.red).updateValue(it)
    }
}
