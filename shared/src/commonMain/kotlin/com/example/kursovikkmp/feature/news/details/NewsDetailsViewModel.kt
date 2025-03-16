package com.example.kursovikkmp.feature.news.details

import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateImage
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.favorites.list.FavoritesRepository
import com.example.kursovikkmp.feature.news.NewsService
import com.example.kursovikkmp.feature.news.model.Article
import kotlinx.coroutines.launch

class NewsDetailsViewModel(private val title: String,
                           private val newsService: NewsService,
                           private val favoritesRepository: FavoritesRepository,
                           ): BaseViewModel<NewsDetailsState, NewsDetailsEvents>() {
    var article: Article? = null

    override fun initToolbar() {
        var titleBar = TitleBarState.getMock()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue(getString(MR.strings.scr_news_screen_title)),
            isNavigateBackVisible = true,
            onDefaultUiEvent = ::onDefaultUiEvent
        )
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initScreenData() {
        val item = newsService.news.find { it.title == title}

        if (item != null) {
            article = item
            updateState { copy(imageUrl = item.urlToImage,
                               dateState = dateState.updateValue(item.publishedAt ?: ""),
                               titleState = titleState.updateValue(item.title ?: ""),
                               textState = textState.updateValue(item.description ?: "")) }

            viewModelScope.launch {
                checkFavorite(title)
            }
        }
    }

    override fun initialState(): NewsDetailsState  = NewsDetailsState()

    override fun onEvent(event: NewsDetailsEvents) {
        when (event) {
            NewsDetailsEvents.OnFavoriteClicked -> {
                viewModelScope.launch {
                    updateFavorite(title)
                }
            }
            NewsDetailsEvents.OnOpenClicked -> {
                deviceService.openUrl(article?.url ?: "")
            }
        }
    }

    private suspend fun checkFavorite(title: String) {
        val check = favoritesRepository.check(title)
        val image = if (check) MR.images.favorite_on_icon else MR.images.favorite_off_icon
        updateState { copy(favoriteButton = favoriteButton.updateImage(image)) }
    }

    private suspend fun updateFavorite(title: String) {
        val check = favoritesRepository.check(title)
        if (!check) {
            article?.let { favoritesRepository.insert(it)}
        } else {
            favoritesRepository.delete(title)
        }

        val image = if (check) MR.images.favorite_off_icon else MR.images.favorite_on_icon
        updateState { copy(favoriteButton = favoriteButton.updateImage(image)) }
    }

}