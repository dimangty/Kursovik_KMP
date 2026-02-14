package com.example.kursovikkmp.feature.recipes.list

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.recipes.RecipesService
import com.example.kursovikkmp.feature.recipes.model.Recipe
import com.example.kursovikkmp.navigation.NavigationAction
import kotlinx.coroutines.launch

class RecipesListViewModel(
    private val recipesService: RecipesService
) : BaseViewModel<RecipesListState, RecipesListEvents>() {

    private var recipes: List<Recipe> = emptyList()

    override fun initScreenData() {
        viewModelScope.launch {
            loadRecipes()
        }
    }

    override fun onScreenResumed() {
        if (recipes.isNotEmpty()) {
            updateState { copy(recipesItems = recipes.mapToUiItems()) }
        }
    }

    override fun initToolbar() {
        var titleBar = state.titleBarState.copy()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue(getString(MR.strings.scr_recipes_screen_title)),
            isNavigateBackVisible = false
        )
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initialState() = RecipesListState()

    override fun onEvent(event: RecipesListEvents) {
        when (event) {
            RecipesListEvents.OnRetryClicked -> {
                viewModelScope.launch {
                    loadRecipes()
                }
            }
            is RecipesListEvents.OnItemClicked -> {
                navigate(NavigationAction.NavigateToRecipesDetails(event.recipeId))
            }
        }
    }

    private suspend fun loadRecipes() {
        lceStateManager.showLoading()
        try {
            recipes = recipesService.getRecipes()
            updateState { copy(recipesItems = recipes.mapToUiItems()) }
        } catch (t: Throwable) {
            showError(t.message ?: "Error")
        } finally {
            lceStateManager.hideLoading()
        }
    }

    private fun List<Recipe>.mapToUiItems(): List<RecipeUiState> {
        return map { item ->
            RecipeUiState(
                id = item.id,
                title = item.title,
                text = item.description,
                duration = "⏱ ${item.durationMinutes} мин",
                imageUrl = item.imageUrl
            )
        }
    }
}
