package com.example.kursovikkmp.feature.news.list

import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.TitleBarState
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.extensions.appLog
import com.example.kursovikkmp.feature.news.list.model.Article
import com.example.kursovikkmp.feature.news.list.model.NewsList
import com.example.kursovikkmp.feature.news.list.model.NewsService
import info.javaway.spend_sense.network.ApiErrorWrapper
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class NewsListViewModel(private val newsService: NewsService): BaseViewModel<NewsListState, NewsListEvents>() {

    init {
        initToolbar()
        viewModelScope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                //pushEvent(Event.Error(t.message.orEmpty()))
            }
        ) {
            loadNews()
        }
    }

    override fun initToolbar() {
        var titleBar = TitleBarState.getMock()
        titleBar = titleBar.copy(title = titleBar.title.updateValue("News"),
                                 isNavigateBackVisible = false)
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initialState() = NewsListState()

    override fun onEvent(event: NewsListEvents) {
//        when(event){
//
//        }
    }



    suspend fun loadNews() {
        appLog("loadNews")
            lceStateManager.showLoading()
            val response = newsService.getNews()
            lceStateManager.hideLoading()
            if (response.status.isSuccess()) {
                val regResponse = response.body<NewsList>()
                updateState { copy(newsItems = regResponse.articles.mapToUiItems()) }
            } else {
                val error = response.body<ApiErrorWrapper>().error
                val message = error?.message ?: response.bodyAsText()
                showError(message)
            }
    }

    private fun List<Article>.mapToUiItems(): List<NewsUiState> {
        var items = mutableListOf<NewsUiState>()
        for (item in this) {
            if (item.title != null && item.description != null) {
                items.add(
                    NewsUiState(
                        id = items.count().toString(),
                        title = item.title,
                        text = item.description,
                        imageUrl = item.urlToImage,
                        date = item.publishedAt
                    )
                )
            }
        }
        return items
    }
}