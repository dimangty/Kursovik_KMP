package com.example.kursovikkmp.feature.favorites.details

import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.favorites.list.FavoritesRepository
import com.example.kursovikkmp.feature.news.NewsService
import com.example.kursovikkmp.feature.news.model.Article
import com.example.kursovikkmp.feature.news.model.toDateString
import com.example.kursovikkmp.feature.news.model.toEntity
import kotlinx.coroutines.launch

class FavoriteDetailsViewModel(
    private val title: String,
    private val favoritesRepository: FavoritesRepository,
) : BaseViewModel<FavoriteDetailsState, FavoriteDetailsEvents>() {
    var article: Article? = null

    override fun initToolbar() {
        var titleBar = TitleBarState.getMock()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue("Favorites"),
            isNavigateBackVisible = true,
            onDefaultUiEvent = ::onDefaultUiEvent
        )
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initScreenData() {
        viewModelScope.launch {
            loadData()
        }
    }

    override fun onEvent(event: FavoriteDetailsEvents) {
        when (event) {
            FavoriteDetailsEvents.OnOpenClicked -> {
                deviceService.openUrl(article?.url ?: "")
            }
        }
    }

    suspend fun loadData() {
        val item = favoritesRepository.get(title)
        if (item != null) {
            article = item.toEntity()
            updateState {
                copy(
                    imageUrl = item.urlToImage,
                    dateState = dateState.updateValue(item.publishedAt.toDateString() ?: ""),
                    titleState = titleState.updateValue(item.title ?: ""),
                    textState = textState.updateValue(item.description ?: "")
                )
            }
        }
    }

    override fun initialState(): FavoriteDetailsState = FavoriteDetailsState.getMock()


}