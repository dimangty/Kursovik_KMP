package com.example.kursovikkmp.feature.auth.login

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextFieldState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.updateEnabled
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.auth.AuthService
import com.example.kursovikkmp.feature.auth.ValidationService
import com.example.kursovikkmp.navigation.NavigationAction
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService,
    private val validationService: ValidationService
) : BaseViewModel<LoginState, LoginEvents>() {

    override fun initToolbar() {
        updateState { copy(titleBarState = titleBarState.copy(isNavigateBackVisible = false)) }
        updateButtonStrings()
    }

    override fun initScreenData() {
        // No initial data to load
    }

    private fun updateButtonStrings() {
        // Button strings are set in the state computed properties
        // This method is for future localization support if needed
    }

    override fun initialState() = LoginState(
        phoneFieldState = TextFieldState(
            value = "",
            placeholder = getString(MR.strings.phone_number),
            keyboardType = TextFieldState.KeyboardType.Phone
        ),
        confirmButtonState = ButtonState.primary(
            value = getString(MR.strings.confirm),
            background = MR.colors.grey
        ).updateEnabled(false),
        signUpButtonState = TextState.latoMedium(14, MR.colors.primary)
            .updateValue(getString(MR.strings.sign_up))
    )

    override fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.PhoneChanged -> {
                val filteredPhone = event.phone.filter { it.isDigit() }.take(15)
                val isValid = validationService.isPhoneValid(filteredPhone)
                updateState {
                    copy(
                        phone = filteredPhone,
                        phoneFieldState = phoneFieldState.updateValue(filteredPhone),
                        isPhoneValid = isValid,
                        errorMessage = null,
                        errorTextState = null,
                        confirmButtonState = ButtonState.primary(
                            value = confirmButtonState.title,
                            background = if (isValid) MR.colors.primary else MR.colors.grey
                        ).updateEnabled(isValid && !isLoading)
                    )
                }
            }

            is LoginEvents.LoginButtonTapped -> {
                if (state.isPhoneValid) {
                    login()
                }
            }

            is LoginEvents.SignUpButtonTapped -> {
                navigate(NavigationAction.NavigateToSignUp)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true,
                    errorMessage = null,
                    errorTextState = null,
                    confirmButtonState = confirmButtonState.updateEnabled(false)
                )
            }

            authService.login(state.phone)
                .onSuccess {
                    updateState {
                        copy(
                            isLoading = false,
                            confirmButtonState = ButtonState.primary(
                                value = confirmButtonState.title,
                                background = if (isPhoneValid) MR.colors.primary else MR.colors.grey
                            ).updateEnabled(isPhoneValid)
                        )
                    }
                    navigate(NavigationAction.NavigateToPin)
                }
                .onFailure { error ->
                    val errorMsg = "Invalid phone number"
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = errorMsg,
                            errorTextState = TextState.latoRegular(12, MR.colors.red).updateValue(errorMsg),
                            confirmButtonState = ButtonState.primary(
                                value = confirmButtonState.title,
                                background = if (isPhoneValid) MR.colors.primary else MR.colors.grey
                            ).updateEnabled(isPhoneValid)
                        )
                    }
                }
        }
    }
}
