package com.example.kursovikkmp.feature.news.details

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.news.list.NewsUiState

data class NewsDetailsState(val imageUrl: String? = null,
                            val dateState: TextState = TextState.latoRegular(13, MR.colors.black),
                            val titleState: TextState = TextState.latoSemibold(17, MR.colors.black),
                            val textState: TextState = TextState.latoRegular(13, MR.colors.black),
                            val favoriteButton: ButtonState = ButtonState.image(image = MR.images.favorite_off_icon),
                            val openButton: ButtonState = ButtonState.primary("Open"),
                            override val titleBarState: TitleBarState = TitleBarState.getMock()) : BaseViewState {
    companion object {
        fun getMock() = NewsDetailsState().run {
            copy(
                dateState = dateState.updateValue("3 march"),
                titleState = titleState.updateValue("title"),
                textState = textState.updateValue("text"),
                openButton = openButton.updateValue("Open"),
                imageUrl = "https://cdnstatic.rg.ru/uploads/images/2025/02/25/zagruzhennoe_f42.jpg",
            )
        }
    }

}