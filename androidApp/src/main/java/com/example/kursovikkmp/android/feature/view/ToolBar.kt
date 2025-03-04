package com.example.kursovikkmp.android.feature.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.android.Common.Extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.kursovikkmp.android.Common.Extensions.color
import com.example.kursovikkmp.android.MyApplicationTheme
import com.example.kursovikkmp.base.DefaultUiEvent
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState

private val ToolbarHeight = 40.dp

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    toolbarState: TitleBarState = TitleBarState(),
) {
    ToolbarWithContent(
        onNavigateBackClicked = { toolbarState.onDefaultUiEvent(DefaultUiEvent.OnBackClicked) },
        navigateBackIcon = toolbarState.backIcon.drawableResId,
        modifier = modifier,
        title = toolbarState.title,
        isNavigateBackVisible = toolbarState.isNavigateBackVisible,
        contentColor = toolbarState.contentColor.color(),
    )
}

@Composable
fun ToolbarWithContent(
    onNavigateBackClicked: () -> Unit,
    title: TextState,
    contentColor: Color = MR.colors.white.color(),
    navigateBackIcon: Int = MR.images.ic_titlebar_back.drawableResId,
    modifier: Modifier = Modifier,
    isNavigateBackVisible: Boolean = true,
    endContent: @Composable RowScope.() -> Unit = {},
) {
    Column {
        Spacer(
            Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(Color.Transparent)
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(ToolbarHeight),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (title.value.isNotBlank()) {
                MyText(
                    state = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 50.dp)
                )
            }

            if (isNavigateBackVisible) {
                val focusManager = LocalFocusManager.current
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        onNavigateBackClicked()
                    },
                    modifier = Modifier.padding(start = 2.dp),
                    content = {
                        Icon(
                            painter = painterResource(navigateBackIcon),
                            contentDescription = null,
                            tint = contentColor,
                        )
                    }
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 2.dp)
            ) {
                endContent()
            }
        }
    }
}

@Suppress("MagicNumber")
@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PreviewToolbar() {
    MyApplicationTheme  {
        Column {
            Toolbar(
                toolbarState = TitleBarState.getMock(),
            )
        }
    }
}