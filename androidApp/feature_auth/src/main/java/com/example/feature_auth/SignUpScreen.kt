package com.example.feature_auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.BaseScreen
import com.example.core.MyButton
import com.example.core.MyDropdownField
import com.example.core.MyText
import com.example.core.MyTextField
import com.example.core.Toolbar
import com.example.core.VSpacer
import com.example.kursovikkmp.feature.auth.signup.SignUpEvents
import com.example.kursovikkmp.feature.auth.signup.SignUpState
import com.example.kursovikkmp.feature.auth.signup.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen() {
    val viewModel: SignUpViewModel = koinViewModel()
    val state by viewModel.flowState.collectAsState()
    val lceState by viewModel.lceState.collectAsState()

    BaseScreen(
        lceState = lceState,
        onDefaultUiEvent = viewModel::onDefaultUiEvent
    ) {
        SignUpScreenView(
            state = state,
            onUiEvent = viewModel::pushEvent
        )
    }
}

@Composable
fun SignUpScreenView(
    state: SignUpState,
    onUiEvent: (SignUpEvents) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(toolbarState = state.titleBarState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Personal Information",
                style = MaterialTheme.typography.titleMedium
            )

            // All fields configured from shared layer
            MyTextField(
                state = state.firstNameField,
                onValueChange = { onUiEvent(SignUpEvents.FirstNameChanged(it)) }
            )

            MyTextField(
                state = state.lastNameField,
                onValueChange = { onUiEvent(SignUpEvents.LastNameChanged(it)) }
            )

            MyDropdownField(
                state = state.genderField,
                onValueChange = { onUiEvent(SignUpEvents.GenderChanged(it)) }
            )

            MyTextField(
                state = state.birthDateField,
                onValueChange = { onUiEvent(SignUpEvents.BirthDateChanged(it)) }
            )

            VSpacer(8.dp)

            Text(
                text = "Location",
                style = MaterialTheme.typography.titleMedium
            )

            MyDropdownField(
                state = state.countryField,
                onValueChange = { onUiEvent(SignUpEvents.CountryChanged(it)) }
            )

            MyDropdownField(
                state = state.cityField,
                onValueChange = { onUiEvent(SignUpEvents.CityChanged(it)) }
            )

            VSpacer(8.dp)

            Text(
                text = "Contact",
                style = MaterialTheme.typography.titleMedium
            )

            MyTextField(
                state = state.emailField,
                onValueChange = { onUiEvent(SignUpEvents.EmailChanged(it)) }
            )

            MyTextField(
                state = state.phoneField,
                onValueChange = { onUiEvent(SignUpEvents.PhoneChanged(it)) }
            )

            MyButton(
                state = state.createAccountButton,
                onClick = { onUiEvent(SignUpEvents.CreateAccountTapped) }
            )

            // Error text configured from shared layer
            state.errorTextState?.let { errorState ->
                MyText(state = errorState)
            }
        }
    }
}
