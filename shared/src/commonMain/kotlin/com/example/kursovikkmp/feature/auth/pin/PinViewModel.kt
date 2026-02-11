package com.example.kursovikkmp.feature.auth.pin

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextFieldState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.updateEnabled
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.auth.AuthService
import com.example.kursovikkmp.navigation.NavigationAction
import kotlinx.coroutines.launch

class PinViewModel(
    private val authService: AuthService
) : BaseViewModel<PinState, PinEvents>() {

    override fun initToolbar() {
        updateState { copy(titleBarState = titleBarState.copy(isNavigateBackVisible = true)) }
    }

    override fun initScreenData() {
        // No initial data to load
    }

    override fun initialState(): PinState = PinState(
        pinFieldState = TextFieldState(
            value = "",
            placeholder = getString(MR.strings.pin_placeholder),
            keyboardType = TextFieldState.KeyboardType.Number
        ),
        confirmButtonState = ButtonState.primary(
            value = getString(MR.strings.confirm_pin),
            background = MR.colors.grey
        ).updateEnabled(false),
        helperTextState = TextState.latoRegular(12, MR.colors.grey).updateValue(
            getString(MR.strings.pin_helper_text)
        )
    )

    override fun onEvent(event: PinEvents) {
        when (event) {
            is PinEvents.PinChanged -> {
                val filteredPin = event.pin.filter { it.isDigit() }.take(6)
                val isValid = filteredPin.length == 6
                updateState {
                    copy(
                        pin = filteredPin,
                        isPinValid = isValid,
                        errorMessage = null,
                        errorTextState = null,
                        pinFieldState = pinFieldState.updateValue(filteredPin),
                        confirmButtonState = ButtonState.primary(
                            value = confirmButtonState.title,
                            background = if (isValid) MR.colors.primary else MR.colors.grey
                        ).updateEnabled(isValid && !isLoading)
                    )
                }
            }

            PinEvents.ConfirmTapped -> {
                if (state.isPinValid) {
                    verifyPin()
                }
            }
        }
    }

    private fun verifyPin() {
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true,
                    errorMessage = null,
                    errorTextState = null,
                    confirmButtonState = confirmButtonState.updateEnabled(false)
                )
            }

            authService.verifyPin(state.pin)
                .onSuccess {
                    updateState {
                        copy(
                            isLoading = false,
                            confirmButtonState = confirmButtonState.updateEnabled(isPinValid)
                        )
                    }
                    navigate(NavigationAction.NavigateToMain)
                }
                .onFailure {
                    val errorMsg = getString(MR.strings.invalid_pin)
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = errorMsg,
                            errorTextState = TextState.latoRegular(12, MR.colors.red).updateValue(errorMsg),
                            confirmButtonState = confirmButtonState.updateEnabled(isPinValid)
                        )
                    }
                }
        }
    }
}
