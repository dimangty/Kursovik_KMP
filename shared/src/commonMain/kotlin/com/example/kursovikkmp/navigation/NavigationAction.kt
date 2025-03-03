package com.example.kursovikkmp.navigation

import com.example.kursovikkmp.feature.news.model.Article
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationAction {
    @Serializable
    class NavigateToNewsDetails(val title: String) : NavigationAction()

    @Serializable
    class NavigateToFavoritesDetails(val title: String) : NavigationAction()

    @Serializable
    data object NavigateBack : NavigationAction()

}