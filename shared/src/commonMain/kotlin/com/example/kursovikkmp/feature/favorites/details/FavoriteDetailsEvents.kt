package com.example.kursovikkmp.feature.favorites.details

import com.example.kursovikkmp.base.BaseEvent

sealed class FavoriteDetailsEvents: BaseEvent {
    data object OnOpenClicked : FavoriteDetailsEvents()
}