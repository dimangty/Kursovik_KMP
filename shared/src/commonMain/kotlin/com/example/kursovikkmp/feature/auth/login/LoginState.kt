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
    // UI Components
    val phoneFieldState: TextFieldState = TextFieldState(
        value = "",
        placeholder = "",
        keyboardType = TextFieldState.KeyboardType.Phone
    ),
    val confirmButtonState: ButtonState = ButtonState.primary(
        value = "",
        background = MR.colors.grey
    ),
    val signUpButtonState: TextState = TextState.latoMedium(14, MR.colors.primary)
        .updateValue(""),
    val errorTextState: TextState? = null,
    override val titleBarState: TitleBarState = TitleBarState.getMock()
) : BaseViewState
