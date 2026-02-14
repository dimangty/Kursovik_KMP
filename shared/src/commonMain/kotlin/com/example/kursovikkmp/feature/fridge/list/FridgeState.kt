package com.example.kursovikkmp.feature.fridge.list

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import dev.icerock.moko.resources.ColorResource

data class FridgeState(
    val productsItems: List<FridgeProductUiState> = emptyList(),
    val recommendationsItems: List<FridgeRecommendedRecipeUiState> = emptyList(),
    val productsTitleState: TextState = TextState.latoSemibold(17, MR.colors.black),
    val recommendationsTitleState: TextState = TextState.latoSemibold(17, MR.colors.black),
    val emptyRecommendationsState: TextState = TextState.latoRegular(14, MR.colors.black),
    val recommendButtonTitle: String = "Рекомендовать рецепты",
    val isRecommendButtonEnabled: Boolean = false,
    val hasRecommendationsRequest: Boolean = false,
    override val titleBarState: TitleBarState = TitleBarState.getMock(),
    val backGroundColor: ColorResource = MR.colors.grey
) : BaseViewState {
    companion object {
        fun getMock() = FridgeState(
            productsItems = listOf(
                FridgeProductUiState(id = "1", name = "Тофу", isSelected = true),
                FridgeProductUiState(id = "2", name = "Рис", isSelected = true),
                FridgeProductUiState(id = "3", name = "Авокадо", isSelected = false)
            ),
            recommendationsItems = listOf(
                FridgeRecommendedRecipeUiState.getMock()
            ),
            isRecommendButtonEnabled = true,
            hasRecommendationsRequest = true,
            productsTitleState = TextState.latoSemibold(17, MR.colors.black).updateValue("Продукты в холодильнике"),
            recommendationsTitleState = TextState.latoSemibold(17, MR.colors.black).updateValue("Подходящие рецепты"),
            emptyRecommendationsState = TextState.latoRegular(14, MR.colors.black).updateValue("По выбранным продуктам рецепты не найдены")
        )
    }
}

data class FridgeProductUiState(
    val id: String,
    val name: String,
    val isSelected: Boolean
)

data class FridgeRecommendedRecipeUiState(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val imageUrl: String,
    val matchedIngredientsText: String,
    val cellBackground: ColorResource = MR.colors.white
) {
    val titleState: TextState = TextState.latoSemibold(17, MR.colors.black).updateValue(title)
    val descriptionState: TextState = TextState.latoRegular(13, MR.colors.black).updateValue(description)
    val durationState: TextState = TextState.latoRegular(13, MR.colors.black).updateValue(duration)
    val matchedState: TextState = TextState.latoRegular(12, MR.colors.black).updateValue(matchedIngredientsText)

    companion object {
        fun getMock() = FridgeRecommendedRecipeUiState(
            id = "6",
            title = "Поке боул с сыром тофу",
            description = "Поке боул с сыром тофу",
            duration = "⏱ 30 мин",
            imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",
            matchedIngredientsText = "Подходит по продуктам: Тофу, Рис, Авокадо"
        )
    }
}
