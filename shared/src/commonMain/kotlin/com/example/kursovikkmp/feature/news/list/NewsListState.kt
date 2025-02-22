package com.example.kursovikkmp.feature.news.list

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.ButtonData
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import dev.icerock.moko.resources.ImageResource

data class NewsListState(
    val newsItems: List<NewsUiState> = listOf(),
    override val titleBarState: TitleBarState = TitleBarState.getMock(),
) : BaseViewState

data class NewsUiState(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    val date: String = "",
    val imageUrl: String? = "",
    val favorite: Boolean = false,
    val favoriteButton: ButtonState = ButtonState.image(image = MR.images.favorite_off_icon),
) {
    val titleState: TextState = TextState.latoSemibold(17, MR.colors.black).updateValue(title)
    val textState: TextState = TextState.latoRegular(13, MR.colors.black).updateValue(text)
    val dateState: TextState = TextState.latoRegular(13, MR.colors.black).updateValue(date)
}