package com.example.kursovikkmp.feature.recipes.details

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.recipes.RecipesService
import kotlinx.coroutines.launch

class RecipesDetailsViewModel(
    private val recipeId: String,
    private val recipesService: RecipesService
) : BaseViewModel<RecipesDetailsState, RecipesDetailsEvents>() {

    override fun initToolbar() {
        var titleBar = TitleBarState.getMock()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue(getString(MR.strings.scr_recipe_details_screen_title)),
            isNavigateBackVisible = true,
            onDefaultUiEvent = ::onDefaultUiEvent
        )
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initScreenData() {
        viewModelScope.launch {
            loadRecipeDetails()
        }
    }

    override fun onEvent(event: RecipesDetailsEvents) {
        when (event) {
            RecipesDetailsEvents.OnRetryClicked -> {
                viewModelScope.launch {
                    loadRecipeDetails()
                }
            }
        }
    }

    override fun initialState(): RecipesDetailsState = RecipesDetailsState.getMock()

    private suspend fun loadRecipeDetails() {
        lceStateManager.showLoading()
        try {
            val recipe = recipesService.getRecipeById(recipeId)
            if (recipe == null) {
                showError("Recipe not found")
                return
            }

            updateState {
                copy(
                    imageUrl = recipe.imageUrl,
                    titleState = titleState.updateValue(recipe.title),
                    descriptionState = descriptionState.updateValue(recipe.description),
                    durationState = durationState.updateValue("⏱ ${recipe.durationMinutes} мин"),
                    ingredientsTitleState = ingredientsTitleState.updateValue(getString(MR.strings.scr_recipe_ingredients_title)),
                    ingredientsItems = recipe.ingredients.map { item ->
                        RecipeIngredientUiState(
                            name = item.name,
                            amount = item.amount
                        )
                    },
                    ingredientsState = ingredientsState.updateValue(recipe.ingredients.joinToString("\n") { "• ${it.name}" }),
                    stepsTitleState = stepsTitleState.updateValue(getString(MR.strings.scr_recipe_steps_title)),
                    stepsItems = recipe.steps.mapIndexed { index, step ->
                        RecipeStepUiState(
                            number = index + 1,
                            text = step.text,
                            duration = step.durationMinutes.toStepDuration()
                        )
                    },
                    stepsState = stepsState.updateValue(recipe.steps.mapIndexed { index, step -> "${index + 1}. ${step.text}" }.joinToString("\n"))
                )
            }
        } catch (t: Throwable) {
            showError(t.message ?: "Error")
        } finally {
            lceStateManager.hideLoading()
        }
    }
}

private fun Int?.toStepDuration(): String {
    if (this == null) return ""
    val normalized = this.coerceAtLeast(0)
    return if (normalized < 10) "0$normalized:00" else "$normalized:00"
}
