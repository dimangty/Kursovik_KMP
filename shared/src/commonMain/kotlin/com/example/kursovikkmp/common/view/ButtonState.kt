package com.example.kursovikkmp.common.view

import com.example.kursovikkmp.MR
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.ImageResource

@Suppress("MagicNumber")
data class ButtonState(
    val data: ButtonData,
    val isEnabled: Boolean = true,
) {
    val textState: TextState
        get() = data.textState

    val backgroundColor: ColorResource
        get() = data.color

    val image: ImageResource?
        get() = when (data) {
            is ButtonData.PrimaryButton -> textState.iconStart
            is ButtonData.ImageButton -> data.image
        }

    val title: String
        get() = when (data) {
            is ButtonData.PrimaryButton -> data.text
            is ButtonData.ImageButton -> data.text
        }

    val coloredState: ButtonData.ColoredState
        get() = when (data) {
            is ButtonData.PrimaryButton -> data.coloredState
            is ButtonData.ImageButton -> ButtonData.ColoredState.Colored
        }

    companion object {

        fun primary(
            value: String = "",
            image: ImageResource? = null,
            background: ColorResource = MR.colors.primary,
            coloredState: ButtonData.ColoredState = ButtonData.ColoredState.Colored,
        ) = ButtonState(data = ButtonData.PrimaryButton(value, image, background, coloredState))

        fun image(
            value: String = "",
            image: ImageResource? = null,
            background: ColorResource = MR.colors.white,
            coloredState: ButtonData.ColoredState = ButtonData.ColoredState.Colored,
        ) = ButtonState(data = ButtonData.ImageButton(value, background, image))


    }
}

fun ButtonState.updateEnabled(enabled: Boolean) = this.copy(
    isEnabled = enabled,
)

fun ButtonState.updateValue(value: String) = this.copy(data = this.data.updateText(value))

fun ButtonState.getMock(value: String = "") = this.updateValue(value.ifEmpty { "Button" })

fun ButtonState.updateImage(image: ImageResource) = when (this.data) {
    is ButtonData.PrimaryButton -> this.copy(data = this.data.updateImage(image))
    is ButtonData.ImageButton -> this.copy(data = this.data.updateImage(image))
}

@Suppress("MagicNumber")
sealed class ButtonData(open val text: String) {

    open val textState: TextState = TextState.latoMedium(14, MR.colors.white)
    open val color: ColorResource = MR.colors.primary

    enum class ColoredState { Colored, Transparent }

    data class PrimaryButton(
        override val text: String,
        val imageStart: ImageResource? = null,
        val background: ColorResource,
        val coloredState: ColoredState = ColoredState.Colored,
    ) : ButtonData(text) {
        override val textState: TextState
            get() = TextState.latoMedium(14, MR.colors.white).copy(iconStart = imageStart)
                .updateValue(text)

        override fun updateText(text: String): ButtonData {
            return this.copy(text = text)
        }

        fun updateImage(image: ImageResource): ButtonData {
            return this.copy(imageStart = image)
        }

        override val color: ColorResource
            get() = background

        companion object {
            fun getMock() = PrimaryButton("Button",
                                          background = MR.colors.red)
        }
    }

    enum class Alignment { Start, Center }

    data class ImageButton(
        override val text: String,
        val background: ColorResource,
        val image: ImageResource? = null,
        val alignment: Alignment = Alignment.Center,
    ) : ButtonData(text) {
        override val textState: TextState
            get() = TextState.latoMedium(14, background).copy(iconStart = image).updateValue(text)

        override fun updateText(text: String): ButtonData {
            return this.copy(text = text)
        }

        override val color: ColorResource
            get() = background

        fun updateImage(image: ImageResource): ButtonData {
            return this.copy(image = image)
        }

        companion object {
            fun getMock() = ImageButton("Button",
                                        image = MR.images.favorite_off_icon,
                                        background = MR.colors.black)
        }
    }

    abstract fun updateText(text: String): ButtonData
}