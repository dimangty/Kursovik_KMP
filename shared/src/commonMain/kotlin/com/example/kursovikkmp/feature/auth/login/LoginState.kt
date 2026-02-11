package com.example.kursovikkmp.feature.auth.login

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextFieldState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.getMock
import com.example.kursovikkmp.common.view.updateEnabled
import com.example.kursovikkmp.common.view.updateValue

data class LoginState(
    val phone: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isPhoneValid: Boolean = false,
    override val titleBarState: TitleBarState = TitleBarState.getMock()
) : BaseViewState {

    // UI Components configured from shared layer
    val phoneFieldState: TextFieldState = TextFieldState(
        value = phone,
        placeholder = MR.strings.phone_number,
        keyboardType = TextFieldState.KeyboardType.Phone,
        error = null
    )

    val confirmButtonState: ButtonState = ButtonState.primary(
        value = "Confirm", // Will be set by ViewModel with actual string
        background = if (isPhoneValid) MR.colors.primary else MR.colors.grey
    ).updateEnabled(isPhoneValid && !isLoading)

    val signUpButtonState: TextState = TextState.latoMedium(14, MR.colors.primary)
        .updateValue("Sign Up") // Will be set by ViewModel

    val errorTextState: TextState? = errorMessage?.let {
        TextState.latoRegular(12, MR.colors.red).updateValue(it)
    }
}
