package com.example.kursovikkmp.feature.favorites.list

import com.example.kursovikkmp.base.BaseEvent

sealed class FavoritesListEvents: BaseEvent {
    class OnFavoriteClicked(val title: String) : FavoritesListEvents()
    class OnItemClicked(val title: String) : FavoritesListEvents()
}