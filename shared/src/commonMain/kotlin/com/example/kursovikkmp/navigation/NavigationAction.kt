package com.example.kursovikkmp.navigation

import com.example.kursovikkmp.feature.news.model.Article
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationAction {
    @Serializable
    data object NavigateToLogin : NavigationAction()

    @Serializable
    data object NavigateToSignUp : NavigationAction()

    @Serializable
    data object NavigateToPin : NavigationAction()

    @Serializable
    data object NavigateToMain : NavigationAction()

    @Serializable
    class NavigateToNewsDetails(val title: String) : NavigationAction()

    @Serializable
    class NavigateToFavoritesDetails(val title: String) : NavigationAction()

    @Serializable
    class NavigateToRecipesDetails(val recipeId: String) : NavigationAction()

    @Serializable
    data object NavigateBack : NavigationAction()

}
