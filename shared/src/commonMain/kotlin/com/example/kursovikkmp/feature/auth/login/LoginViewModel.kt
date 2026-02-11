package com.example.kursovikkmp.feature.auth.login

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.base.BaseViewModel
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

    override fun initialState() = LoginState()

    override fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.PhoneChanged -> {
                val filteredPhone = event.phone.filter { it.isDigit() }.take(15)
                val isValid = validationService.isPhoneValid(filteredPhone)
                updateState {
                    copy(
                        phone = filteredPhone,
                        isPhoneValid = isValid,
                        errorMessage = null
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
            updateState { copy(isLoading = true, errorMessage = null) }

            authService.login(state.phone)
                .onSuccess {
                    updateState { copy(isLoading = false) }
                    navigate(NavigationAction.NavigateToMain)
                }
                .onFailure { error ->
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = "Invalid phone number"
                        )
                    }
                }
        }
    }
}
