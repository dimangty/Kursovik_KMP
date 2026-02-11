package com.example.feature_auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.BaseScreen
import com.example.core.MyButton
import com.example.core.MyText
import com.example.core.MyTextField
import com.example.core.VSpacer
import com.example.kursovikkmp.feature.auth.login.LoginEvents
import com.example.kursovikkmp.feature.auth.login.LoginState
import com.example.kursovikkmp.feature.auth.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.flowState.collectAsState()
    val lceState by viewModel.lceState.collectAsState()

    BaseScreen(
        lceState = lceState,
        onDefaultUiEvent = viewModel::onDefaultUiEvent
    ) {
        LoginScreenView(
            state = state,
            onUiEvent = viewModel::pushEvent
        )
    }
}

@Composable
fun LoginScreenView(
    state: LoginState,
    onUiEvent: (LoginEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Phone field configured from shared layer
        MyTextField(
            state = state.phoneFieldState,
            onValueChange = { onUiEvent(LoginEvents.PhoneChanged(it)) }
        )

        VSpacer(20.dp)

        // Confirm button configured from shared layer
        MyButton(
            state = state.confirmButtonState,
            onClick = { onUiEvent(LoginEvents.LoginButtonTapped) }
        )

        VSpacer(16.dp)

        // Sign Up text button configured from shared layer
        MyText(
            state = state.signUpButtonState,
            modifier = Modifier.clickable { onUiEvent(LoginEvents.SignUpButtonTapped) }
        )

        // Error text configured from shared layer
        state.errorTextState?.let { errorState ->
            VSpacer(16.dp)
            MyText(state = errorState)
        }
    }
}
