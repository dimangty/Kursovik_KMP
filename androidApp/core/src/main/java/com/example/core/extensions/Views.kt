package com.example.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.kursovikkmp.common.view.TextFontState
import dev.icerock.moko.resources.ColorResource

const val COMPOSE_PREVIEW_BACKGROUND_COLOR = 0xFFFFFFFF

@Composable
fun ColorResource.color(): Color {
    return Color(this.getColor(LocalContext.current))
}

fun TextFontState.textStyle(): TextStyle {
    return TextStyle(
        fontFamily = FontFamily(Font(this.font.fontResourceId)),
        fontSize = this.fontSize.sp,
        lineHeight = this.lineHeight.sp,
    )
}