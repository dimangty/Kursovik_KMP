package com.example.kursovikkmp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationAction {
    @Serializable
    class NavigateToNewsDetails(val newsId: Int) : NavigationAction()

    @Serializable
    class NavigateToFavoritesDetails(val newsId: Int) : NavigationAction()

    @Serializable
    data object NavigateBack : NavigationAction()

}