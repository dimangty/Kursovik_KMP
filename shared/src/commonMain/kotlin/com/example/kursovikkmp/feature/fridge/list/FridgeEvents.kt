package com.example.kursovikkmp.feature.fridge.list

import com.example.kursovikkmp.base.BaseEvent

sealed class FridgeEvents : BaseEvent {
    data class OnProductClicked(val productId: String) : FridgeEvents()
    data class OnRecipeClicked(val recipeId: String) : FridgeEvents()
    data object OnRecommendRecipesClicked : FridgeEvents()
    data object OnRetryClicked : FridgeEvents()
}
