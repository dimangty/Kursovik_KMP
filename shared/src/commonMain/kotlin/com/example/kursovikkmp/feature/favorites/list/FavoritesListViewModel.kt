package com.example.kursovikkmp.feature.favorites.list

import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.news.model.Article
import com.example.kursovikkmp.feature.news.model.toDateString
import kotlinx.coroutines.launch

class FavoritesListViewModel(private val favoritesRepository: FavoritesRepository) :
    BaseViewModel<FavoritesListState, FavoritesListEvents>() {
    var favorites: MutableList<Article> = mutableListOf()

    override fun initScreenData() {
        loadFavoriteNews()
    }

    override fun onScreenResumed() {
        loadFavoriteNews()
    }

    override fun initToolbar() {
        var titleBar = TitleBarState.getMock()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue("Favorites"),
            isNavigateBackVisible = false
        )
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initialState() = FavoritesListState()

    override fun onEvent(event: FavoritesListEvents) {
        when(event){
            is FavoritesListEvents.OnFavoriteClicked -> {
                viewModelScope.launch {
                    updateFavorite(event.title)
                }
            }
        }
    }

    private fun loadFavoriteNews() {
        favorites = favoritesRepository.getAllFlow().toMutableList()
        updateState { copy(favoritesItems = favorites.mapToUiItems()) }
    }

    private fun List<Article>.mapToUiItems(): List<FavoriteUiState> {
        var items = mutableListOf<FavoriteUiState>()
        for (item in this) {
            if (item.title != null && item.description != null) {
                items.add(
                    FavoriteUiState(
                        id = items.count().toString(),
                        title = item.title,
                        text = item.description,
                        imageUrl = item.urlToImage,
                        date = item.publishedAt.toDateString()
                    )
                )
            }
        }
        return items
    }

    private suspend fun updateFavorite(title: String) {
        val index = favorites.indexOfFirst { it.title == title }

        if (index != -1) {
            favorites.removeAt(index)
            favoritesRepository.delete(title)
            updateState { copy(favoritesItems = favorites.mapToUiItems()) }
        }
    }
}