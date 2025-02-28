package com.example.kursovikkmp.android.Common.Extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.resources.ColorResource

@Composable
fun ColorResource.color(): Color {
    return Color(this.getColor(LocalContext.current))
}