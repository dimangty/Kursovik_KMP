package com.example.kursovikkmp.feature.auth.signup

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.auth.AuthService
import com.example.kursovikkmp.feature.auth.SignUpData
import com.example.kursovikkmp.feature.auth.ValidationService
import com.example.kursovikkmp.navigation.NavigationAction
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authService: AuthService,
    private val validationService: ValidationService
) : BaseViewModel<SignUpState, SignUpEvents>() {

    override fun initToolbar() {
        var titleBar = state.titleBarState.copy()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue(getString(MR.strings.sign_up_title)),
            isNavigateBackVisible = true
        )
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initScreenData() {
        // No initial data to load
        // Button strings are configured in state computed properties
    }

    override fun initialState() = SignUpState()

    override fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.FirstNameChanged -> {
                updateState { copy(firstName = event.value, errorMessage = null) }
            }

            is SignUpEvents.LastNameChanged -> {
                updateState { copy(lastName = event.value, errorMessage = null) }
            }

            is SignUpEvents.GenderChanged -> {
                updateState { copy(gender = event.value, errorMessage = null) }
            }

            is SignUpEvents.BirthDateChanged -> {
                updateState { copy(birthDate = event.value, errorMessage = null) }
            }

            is SignUpEvents.CountryChanged -> {
                updateState { copy(country = event.value, errorMessage = null) }
            }

            is SignUpEvents.CityChanged -> {
                updateState { copy(city = event.value, errorMessage = null) }
            }

            is SignUpEvents.EmailChanged -> {
                updateState { copy(email = event.value, errorMessage = null) }
            }

            is SignUpEvents.PhoneChanged -> {
                val filteredPhone = event.value.filter { it.isDigit() }.take(15)
                updateState { copy(phone = filteredPhone, errorMessage = null) }
            }

            is SignUpEvents.CreateAccountTapped -> {
                if (state.isFormValid) {
                    createAccount()
                }
            }

            is SignUpEvents.BackButtonTapped -> {
                navigateBack()
            }
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

            val signUpData = SignUpData(
                firstName = state.firstName,
                lastName = state.lastName,
                gender = state.gender,
                birthDate = state.birthDate,
                country = state.country,
                city = state.city,
                email = state.email,
                phone = state.phone
            )

            authService.signUp(signUpData)
                .onSuccess {
                    updateState { copy(isLoading = false) }
                    navigate(NavigationAction.NavigateToMain)
                }
                .onFailure { error ->
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = getString(MR.strings.sign_up_failed)
                        )
                    }
                }
        }
    }
}
