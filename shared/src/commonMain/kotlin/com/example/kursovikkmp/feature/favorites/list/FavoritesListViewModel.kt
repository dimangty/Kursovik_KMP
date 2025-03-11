package com.example.kursovikkmp.feature.favorites.list

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.mvvm.ErrorState
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.news.model.Article
import com.example.kursovikkmp.feature.news.model.toDateString
import com.example.kursovikkmp.navigation.NavigationAction
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

            is FavoritesListEvents.OnItemClicked -> {
                navigate(NavigationAction.NavigateToFavoritesDetails(event.title))
            }
        }
    }

    private fun loadFavoriteNews() {
        favorites = favoritesRepository.getAllFlow().toMutableList()
        updateState { copy(favoritesItems = favorites.mapToUiItems()) }
    }

    private fun List<Article>.mapToUiItems(): List<FavoriteUiState> {
        return this.filter { it.title != null && it.description != null }
            .mapIndexed { index, item ->
                FavoriteUiState(
                    id = index.toString(),
                    title = item.title ?: "",
                    text = item.description ?: "",
                    imageUrl = item.urlToImage,
                    date = item.publishedAt.toDateString()
                )
            }
    }

    private suspend fun updateFavorite(title: String) {
        val index = favorites.indexOfFirst { it.title == title }

        if (index != -1) {
            showAlert(
                ErrorState.AlertError(
                    title = "Warning",
                    message = "Do you want to delete this news?",
                    positiveButtonText = "Yes",
                    positiveAction = { viewModelScope.launch { deleteFavorite(index, title) } },
                    negativeButtonText = "No",
                    negativeAction = { hideError() }
                ))
        }
    }


    private suspend fun deleteFavorite(index: Int, title: String) {
        favorites.removeAt(index)
        favoritesRepository.delete(title)
        updateState { copy(favoritesItems = favorites.mapToUiItems()) }
        hideError()
    }
}