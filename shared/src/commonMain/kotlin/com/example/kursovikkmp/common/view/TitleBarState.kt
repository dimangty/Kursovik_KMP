package com.example.kursovikkmp.common.view

import com.example.kursovikkmp.MR
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.ImageResource

@Suppress("MagicNumber")
data class TitleBarState(
    val title: TextState = TextState.latoMedium(16, MR.colors.black),
    val isNavigateBackVisible: Boolean = false,
    val backIcon: ImageResource = MR.images.ic_titlebar_back,
    val contentColor: ColorResource = MR.colors.white,
) {
    companion object {
        fun getMock() = TitleBarState().run {
            copy(
                title = title.getMock("Title bar"),
                isNavigateBackVisible = true,
            )
        }
    }
}