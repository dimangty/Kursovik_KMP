package com.example.kursovikkmp.feature.auth.pin

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextFieldState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.getMock
import com.example.kursovikkmp.common.view.updateValue

data class PinState(
    val pin: String = "",
    val isLoading: Boolean = false,
    val isPinValid: Boolean = false,
    val errorMessage: String? = null,
    val pinFieldState: TextFieldState = TextFieldState(
        value = "",
        placeholder = "",
        keyboardType = TextFieldState.KeyboardType.Number
    ),
    val confirmButtonState: ButtonState = ButtonState.primary(
        value = "",
        background = MR.colors.grey
    ),
    val helperTextState: TextState = TextState.latoRegular(12, MR.colors.black).updateValue(""),
    val errorTextState: TextState? = null,
    override val titleBarState: TitleBarState = TitleBarState.getMock()
) : BaseViewState
