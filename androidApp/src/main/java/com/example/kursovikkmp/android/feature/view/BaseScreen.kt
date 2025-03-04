package com.example.kursovikkmp.android.feature.view

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.kursovikkmp.base.DefaultUiEvent
import com.example.kursovikkmp.common.extensions.compose.OnScreenResumed
import com.example.kursovikkmp.common.mvvm.ErrorState
import com.example.kursovikkmp.common.mvvm.LceState
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun BaseScreen(
    lceState: LceState,
    onDefaultUiEvent: (DefaultUiEvent) -> Unit,
    content: @Composable () -> Unit) {

    DisposableEffect(Unit) {
        onDefaultUiEvent(DefaultUiEvent.OnScreenCreated)

        onDispose {
            onDefaultUiEvent(DefaultUiEvent.OnScreenDestroyed)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onDefaultUiEvent(DefaultUiEvent.OnScreenResumed)
    }

    content()

    LceStateHandlerView(
        lceState = lceState
    )
}

@Composable
private fun LceStateHandlerView(
    lceState: LceState
) {
    when (val errorState = lceState.errorState) {
        null -> Unit
        is ErrorState.ApiAlertError -> {
            MyErrorDialog(state = errorState) {
                
            }
        }

        is ErrorState.AlertError -> {
            MyAlertDialog(state = errorState) {

            }
        }
    }

    if (lceState.isLoading) {
        LoadingDialog()
    }
}