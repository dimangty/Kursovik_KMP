package com.example.kursovikkmp.feature.recipes.list

import com.example.kursovikkmp.base.BaseEvent

sealed class RecipesListEvents : BaseEvent {
    data object OnRetryClicked : RecipesListEvents()
    class OnItemClicked(val recipeId: String) : RecipesListEvents()
}
