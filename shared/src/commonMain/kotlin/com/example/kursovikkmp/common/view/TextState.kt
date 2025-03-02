package com.example.kursovikkmp.common.view

import com.example.kursovikkmp.MR
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource

private const val LINE_HEIGHT_SAME_AS_SIZE = -1

@Suppress("SpellCheckingInspection")
data class TextState(
    val fontState: TextFontState,
    val color: ColorResource,
    val value: String = "",
    val iconStart: ImageResource? = null,
    val iconEnd: ImageResource? = null,
    val overrideIconsTint: Boolean = false,
) {
    companion object {
        fun latoRegular(size: Int, color: ColorResource, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            fontState = TextFontState.latoRegular(size, lineHeight),
            color = color,
        )

        fun latoMedium(size: Int, color: ColorResource, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            fontState = TextFontState.latoMedium(size, lineHeight),
            color = color,
        )

        fun latoSemibold(size: Int, color: ColorResource, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            fontState = TextFontState.latoSemibold(size, lineHeight),
            color = color,
        )

        fun latoLight(size: Int, color: ColorResource, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            fontState = TextFontState.latoLight(size, lineHeight),
            color = color,
        )

        private fun initState(fontState: TextFontState, color: ColorResource) = TextState(
            fontState = fontState,
            color = color,
        )
    }
}

fun TextState.getMock(text: String = "") = this.copy(
    value = text.ifEmpty { "Some text" },
)

fun TextState.updateValue(value: String) = this.copy(value = value)

fun TextState.updateIconStart(icon: ImageResource?) = this.copy(iconStart = icon)

fun TextState.updateIconEnd(icon: ImageResource?) = this.copy(iconEnd = icon)

@Suppress("SpellCheckingInspection")
data class TextFontState(
    val font: FontResource,
    val fontSize: Int,
    val lineHeight: Int,
) {
    companion object {
        fun latoLight(size: Int, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            font = MR.fonts.lato_light,
            size = size,
            lineHeight = if (lineHeight == LINE_HEIGHT_SAME_AS_SIZE) size else lineHeight,
        )

        fun latoMedium(size: Int, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            font = MR.fonts.lato_medium,
            size = size,
            lineHeight = if (lineHeight == LINE_HEIGHT_SAME_AS_SIZE) size else lineHeight,
        )

        fun latoSemibold(size: Int, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            font = MR.fonts.lato_semibold,
            size = size,
            lineHeight = if (lineHeight == LINE_HEIGHT_SAME_AS_SIZE) size else lineHeight,
        )

        fun latoRegular(size: Int, lineHeight: Int = LINE_HEIGHT_SAME_AS_SIZE) = initState(
            font = MR.fonts.lato_regular,
            size = size,
            lineHeight = if (lineHeight == LINE_HEIGHT_SAME_AS_SIZE) size else lineHeight,
        )

        private fun initState(font: FontResource, size: Int, lineHeight: Int) = TextFontState(
            font = font,
            fontSize = size,
            lineHeight = lineHeight,
        )
    }
}
