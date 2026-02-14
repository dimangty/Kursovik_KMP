package com.example.kursovikkmp.feature.recipes.details

import com.example.kursovikkmp.base.BaseEvent

sealed class RecipesDetailsEvents : BaseEvent {
    data object OnRetryClicked : RecipesDetailsEvents()
}
