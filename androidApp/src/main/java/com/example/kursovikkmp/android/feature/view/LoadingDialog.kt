package com.example.kursovikkmp.android.feature.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.android.Common.Extensions.color
import com.example.kursovikkmp.android.MyApplicationTheme

@Composable
fun LoadingDialog() {
    LocalFocusManager.current.clearFocus()
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = {}
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                strokeWidth = 6.dp,
                color = MR.colors.red.color(),
                trackColor = MR.colors.loader_background.color().copy(alpha = 0.2f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingDialog() {
    MyApplicationTheme {
        LoadingDialog()
    }
}