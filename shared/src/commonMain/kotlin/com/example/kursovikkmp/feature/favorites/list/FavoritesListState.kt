package com.example.kursovikkmp.feature.favorites.list

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.ButtonState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.news.list.NewsUiState
import dev.icerock.moko.resources.ColorResource

data class FavoritesListState(val favoritesItems: List<FavoriteUiState> = listOf(),
                              override val titleBarState: TitleBarState = TitleBarState.getMock(),
                              val backGroundColor: ColorResource = MR.colors.grey) : BaseViewState {
    companion object {
        fun getMock() = FavoritesListState().run {
            copy(
                favoritesItems = listOf()
            )
        }
    }
}


data class FavoriteUiState(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    val date: String = "",
    val imageUrl: String? = "",
    val favorite: Boolean = false,
    val cellBackground: ColorResource = MR.colors.white,
) {
    val titleState: TextState = TextState.latoSemibold(17, MR.colors.black).updateValue(title)
    val textState: TextState = TextState.latoRegular(13, MR.colors.black).updateValue(text)
    val dateState: TextState = TextState.latoRegular(13, MR.colors.black).updateValue(date)
    val favoriteButton: ButtonState = ButtonState.image(image = MR.images.favorite_on_icon)

    companion object {
        fun getMock() = FavoriteUiState().run {
            copy(
                title = "title",
                text = "text",
                date = "1 march",
                imageUrl = "https://cdnstatic.rg.ru/uploads/images/2025/02/25/zagruzhennoe_f42.jpg",
            )
        }
    }
}