package com.example.kursovikkmp.feature.recipes.details

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue

data class RecipesDetailsState(
    val imageUrl: String? = null,
    val titleState: TextState = TextState.latoSemibold(22, MR.colors.black),
    val descriptionState: TextState = TextState.latoRegular(14, MR.colors.black),
    val durationState: TextState = TextState.latoRegular(13, MR.colors.black),
    val ingredientsTitleState: TextState = TextState.latoSemibold(17, MR.colors.black),
    val ingredientsItems: List<RecipeIngredientUiState> = emptyList(),
    val ingredientsState: TextState = TextState.latoRegular(14, MR.colors.black),
    val stepsTitleState: TextState = TextState.latoSemibold(17, MR.colors.black),
    val stepsItems: List<RecipeStepUiState> = emptyList(),
    val stepsState: TextState = TextState.latoRegular(14, MR.colors.black),
    val startCookingButtonTitle: String = "Начать готовить",
    override val titleBarState: TitleBarState = TitleBarState.getMock()
) : BaseViewState {
    companion object {
        fun getMock() = RecipesDetailsState().run {
            copy(
                imageUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5",
                titleState = titleState.updateValue("Паста Карбонара"),
                descriptionState = descriptionState.updateValue("Классическая паста с беконом и сыром."),
                durationState = durationState.updateValue("⏱ 25 мин"),
                ingredientsTitleState = ingredientsTitleState.updateValue("Ингредиенты"),
                ingredientsItems = listOf(
                    RecipeIngredientUiState("Спагетти", "200 г"),
                    RecipeIngredientUiState("Бекон", "120 г"),
                    RecipeIngredientUiState("Пармезан", "50 г"),
                    RecipeIngredientUiState("Яйца", "2 шт")
                ),
                ingredientsState = ingredientsState.updateValue("• Спагетти\n• Бекон\n• Пармезан"),
                stepsTitleState = stepsTitleState.updateValue("Шаги"),
                stepsItems = listOf(
                    RecipeStepUiState(number = 1, text = "Отвари спагетти до состояния al dente.", duration = "06:00"),
                    RecipeStepUiState(number = 2, text = "Обжарь бекон до золотистой корочки.", duration = "07:00"),
                    RecipeStepUiState(number = 3, text = "Смешай соус и подай горячим.", duration = "12:00")
                ),
                stepsState = stepsState.updateValue("1. Отвари спагетти\n2. Обжарь бекон\n3. Смешай соус")
            )
        }
    }
}

data class RecipeIngredientUiState(
    val name: String,
    val amount: String
)

data class RecipeStepUiState(
    val number: Int,
    val text: String,
    val duration: String
)
