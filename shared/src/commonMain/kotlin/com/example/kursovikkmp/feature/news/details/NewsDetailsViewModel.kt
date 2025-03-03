package com.example.kursovikkmp.feature.news.details

import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.favorites.list.FavoritesListState
import com.example.kursovikkmp.feature.favorites.list.FavoritesRepository
import com.example.kursovikkmp.feature.news.NewsService
import com.example.kursovikkmp.feature.news.model.Article

class NewsDetailsViewModel(private val newsService: NewsService,
                           private val favoritesRepository: FavoritesRepository,
                           private val title: String): BaseViewModel<NewsDetailsState, NewsDetailsEvents>() {
    var article: Article? = null

    override fun initToolbar() {
        var titleBar = TitleBarState.getMock()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue("News"),
            isNavigateBackVisible = false
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
        }
    }

    override fun initialState(): NewsDetailsState  = NewsDetailsState()

    override fun onEvent(event: NewsDetailsEvents) {
        when (event) {
            NewsDetailsEvents.OnFavoriteClicked -> {

            }
            NewsDetailsEvents.OnOpenClicked -> {
                deviceService.openUrl(article?.url ?: "")
            }
        }
    }

}