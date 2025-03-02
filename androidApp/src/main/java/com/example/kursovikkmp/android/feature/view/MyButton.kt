package com.example.kursovikkmp.android.feature.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.android.Common.Extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.kursovikkmp.android.Common.Extensions.color
import com.example.kursovikkmp.android.Common.theme.AppShapes
import com.example.kursovikkmp.android.MyApplicationTheme
import com.example.kursovikkmp.common.view.ButtonData
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.updateValue


@Composable
fun MyButton(
    state: ButtonState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    when (state.data) {
        is ButtonData.PrimaryButton -> {
            PrimaryButton(
                state = state,
                modifier = modifier,
                onClick = onClick,
            )
        }
        is ButtonData.ImageButton -> {
            ImageButton(
                state = state,
                modifier = modifier,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun PrimaryButton(
    state: ButtonState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(AppShapes.primary)
            .background(
                color = if (state.coloredState == ButtonData.ColoredState.Colored) {
                    state.backgroundColor
                        .color()
                        .copy(
                            alpha = if (state.isEnabled) 1f else 0.5f
                        )
                } else {
                    state.backgroundColor.color()
                }
            )
            .clickable(
                enabled = state.isEnabled,
                onClick = {
                    focusManager.clearFocus()
                    onClick()
                }
            )
    ) {
        MyText(state = state.textState)
    }
}

@Composable
private fun ImageButton(
    state: ButtonState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {

    Box(
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        MyText(
            state = state.textState,
            applyDisabledAlpha = !state.isEnabled,
        )
    }
}

@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun MyButtonPreview() {
    MyApplicationTheme  {
        Column(modifier = Modifier.padding(16.dp)) {
            PrimaryButton(state = ButtonState.primary("Text"))
            VSpacer(8.dp)
            ImageButton(state = ButtonState.image(image = MR.images.favorite_on_icon))
        }
    }
}