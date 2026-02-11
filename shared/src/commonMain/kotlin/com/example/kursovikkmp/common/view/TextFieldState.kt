package com.example.kursovikkmp.common.view

import com.example.kursovikkmp.MR
import dev.icerock.moko.resources.ColorResource

data class TextFieldState(
    val value: String = "",
    val placeholder: String,
    val label: String? = null,
    val error: String? = null,
    val isEnabled: Boolean = true,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val backgroundColor: ColorResource = MR.colors.white,
    val textColor: ColorResource = MR.colors.black,
) {
    enum class KeyboardType {
        Text,
        Number,
        Email,
        Phone
    }
}

fun TextFieldState.updateValue(value: String) = this.copy(value = value)

fun TextFieldState.updateError(error: String?) = this.copy(error = error)

fun TextFieldState.updateEnabled(enabled: Boolean) = this.copy(isEnabled = enabled)

data class DropdownFieldState(
    val value: String = "",
    val placeholder: String,
    val label: String? = null,
    val options: List<String> = emptyList(),
    val isEnabled: Boolean = true,
    val backgroundColor: ColorResource = MR.colors.white,
    val textColor: ColorResource = MR.colors.black,
)

fun DropdownFieldState.updateValue(value: String) = this.copy(value = value)

fun DropdownFieldState.updateEnabled(enabled: Boolean) = this.copy(isEnabled = enabled)
