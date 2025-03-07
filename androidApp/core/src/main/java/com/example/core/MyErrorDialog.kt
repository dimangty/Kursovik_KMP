package com.example.core

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kursovikkmp.common.mvvm.ErrorState

@Composable
fun MyErrorDialog(
    state: ErrorState.ApiAlertError,
    onDismissed: () -> Unit,
) {
    LocalFocusManager.current.clearFocus()
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = onDismissed,
    ) {
        BackHandler {
            if (state.isCancellable) {
                onDismissed()
            }
        }

        val interactionSource = remember { MutableInteractionSource() }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = onDismissed,
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = state.isCancellable,
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(AppShapes.rounded)
                    .background(Color.White)
                    .clickable(enabled = false, onClick = {})
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    VSpacer(24.dp)
                    if (state.title.isNotBlank()) {
                        Row {
                            MyText(state = state.titleState, maxLines = 3)
                        }

                        VSpacer(16.dp)
                    }

                    ButtonsView(state = state, onDismissed = onDismissed)
                    VSpacer(24.dp)
                }
            }
        }
    }
}

@Composable
private fun ButtonsView(
    state: ErrorState.ApiAlertError,
    onDismissed: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        MyText(state = state.positiveState,
            modifier = Modifier.clickable(onClick = {
                state.positiveAction()
                onDismissed()
            })
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewMyErrorDialog() {
    MaterialTheme {
        MyErrorDialog(state = ErrorState.ApiAlertError.getMock(),
            onDismissed = {})
    }
}