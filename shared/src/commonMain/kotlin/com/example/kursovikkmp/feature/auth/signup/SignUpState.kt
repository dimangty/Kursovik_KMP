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
    val isFormValid: Boolean = false,
    val errorMessage: String? = null,
    val genderOptions: List<String> = listOf("Male", "Female", "Other"),
    val countryOptions: List<String> = listOf("USA", "UK", "Germany", "France"),
    val cityOptions: List<String> = listOf("New York", "London", "Berlin", "Paris"),
    // UI Components
    val firstNameField: TextFieldState = TextFieldState(
        value = "",
        placeholder = "",
        keyboardType = TextFieldState.KeyboardType.Text
    ),
    val lastNameField: TextFieldState = TextFieldState(
        value = "",
        placeholder = "",
        keyboardType = TextFieldState.KeyboardType.Text
    ),
    val genderField: DropdownFieldState = DropdownFieldState(
        value = "",
        placeholder = "",
        options = genderOptions
    ),
    val birthDateField: TextFieldState = TextFieldState(
        value = "",
        placeholder = "",
        keyboardType = TextFieldState.KeyboardType.Text
    ),
    val countryField: DropdownFieldState = DropdownFieldState(
        value = "",
        placeholder = "",
        options = countryOptions
    ),
    val cityField: DropdownFieldState = DropdownFieldState(
        value = "",
        placeholder = "",
        options = cityOptions
    ),
    val emailField: TextFieldState = TextFieldState(
        value = "",
        placeholder = "",
        keyboardType = TextFieldState.KeyboardType.Email
    ),
    val phoneField: TextFieldState = TextFieldState(
        value = "",
        placeholder = "",
        keyboardType = TextFieldState.KeyboardType.Phone
    ),
    val createAccountButton: ButtonState = ButtonState.primary(
        value = "",
        background = MR.colors.grey
    ),
    val errorTextState: TextState? = null,
    override val titleBarState: TitleBarState = TitleBarState.getMock()
) : BaseViewState
