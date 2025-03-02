package com.example.kursovikkmp.android.Common.Extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.kursovikkmp.common.view.TextFontState
import dev.icerock.moko.resources.ColorResource

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