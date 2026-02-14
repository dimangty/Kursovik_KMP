package com.example.kursovikkmp.feature.fridge.list

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.fridge.FridgeMockNetworkService
import com.example.kursovikkmp.navigation.NavigationAction
import kotlinx.coroutines.launch

class FridgeViewModel(
    private val fridgeMockNetworkService: FridgeMockNetworkService
) : BaseViewModel<FridgeState, FridgeEvents>() {

    override fun initScreenData() {
        viewModelScope.launch {
            loadProducts()
        }
    }

    override fun initToolbar() {
        var titleBar = state.titleBarState.copy()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue(getString(MR.strings.scr_fridge_screen_title)),
            isNavigateBackVisible = false
        )

        updateState {
            copy(
                titleBarState = titleBar,
                productsTitleState = productsTitleState.updateValue(getString(MR.strings.scr_fridge_products_title)),
                recommendationsTitleState = recommendationsTitleState.updateValue(getString(MR.strings.scr_fridge_recommendations_title)),
                emptyRecommendationsState = emptyRecommendationsState.updateValue(getString(MR.strings.scr_fridge_empty_recommendations)),
                recommendButtonTitle = getString(MR.strings.scr_fridge_recommend_button)
            )
        }
    }

    override fun initialState(): FridgeState = FridgeState.getMock().copy(
        productsItems = emptyList(),
        recommendationsItems = emptyList(),
        isRecommendButtonEnabled = false,
        hasRecommendationsRequest = false
    )

    override fun onEvent(event: FridgeEvents) {
        when (event) {
            is FridgeEvents.OnProductClicked -> {
                val newItems = state.productsItems.map { item ->
                    if (item.id == event.productId) {
                        item.copy(isSelected = !item.isSelected)
                    } else {
                        item
                    }
                }

                updateState {
                    copy(
                        productsItems = newItems,
                        isRecommendButtonEnabled = newItems.any { it.isSelected },
                        recommendationsItems = emptyList(),
                        hasRecommendationsRequest = false
                    )
                }
            }
            is FridgeEvents.OnRecipeClicked -> {
                navigate(NavigationAction.NavigateToRecipesDetails(event.recipeId))
            }

            FridgeEvents.OnRecommendRecipesClicked -> {
                viewModelScope.launch {
                    loadRecommendations()
                }
            }

            FridgeEvents.OnRetryClicked -> {
                viewModelScope.launch {
                    loadProducts()
                }
            }
        }
    }

    private suspend fun loadProducts() {
        lceStateManager.showLoading()
        try {
            val products = fridgeMockNetworkService.getFridgeProducts()
            val uiItems = products.map { product ->
                FridgeProductUiState(
                    id = product.id,
                    name = product.name,
                    isSelected = false
                )
            }

            updateState {
                copy(
                    productsItems = uiItems,
                    recommendationsItems = emptyList(),
                    hasRecommendationsRequest = false,
                    isRecommendButtonEnabled = false
                )
            }
        } catch (t: Throwable) {
            showError(t.message ?: "Error")
        } finally {
            lceStateManager.hideLoading()
        }
    }

    private suspend fun loadRecommendations() {
        val selected = state.productsItems
            .filter { it.isSelected }
            .map { it.name }

        updateState {
            copy(
                recommendationsItems = emptyList(),
                hasRecommendationsRequest = false
            )
        }

        if (selected.isEmpty()) {
            updateState {
                copy(
                    recommendationsItems = emptyList(),
                    hasRecommendationsRequest = true
                )
            }
            return
        }

        lceStateManager.showLoading()
        try {
            val recipes = fridgeMockNetworkService.getRecommendedRecipes(selected)
            updateState {
                copy(
                    recommendationsItems = recipes.map { recipe ->
                        FridgeRecommendedRecipeUiState(
                            id = recipe.id,
                            title = recipe.title,
                            description = recipe.description,
                            duration = "⏱ ${recipe.durationMinutes} мин",
                            imageUrl = recipe.imageUrl,
                            matchedIngredientsText = "Подходит по продуктам: ${recipe.matchedIngredients.joinToString()}"
                        )
                    },
                    hasRecommendationsRequest = true
                )
            }
        } catch (t: Throwable) {
            showError(t.message ?: "Error")
        } finally {
            lceStateManager.hideLoading()
        }
    }
}
