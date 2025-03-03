package com.example.kursovikkmp.feature.news.details

import com.example.kursovikkmp.base.BaseEvent
import com.example.kursovikkmp.feature.favorites.list.FavoritesListEvents

sealed class NewsDetailsEvents: BaseEvent {
    data object OnFavoriteClicked : NewsDetailsEvents()
    data object OnOpenClicked : NewsDetailsEvents()
}