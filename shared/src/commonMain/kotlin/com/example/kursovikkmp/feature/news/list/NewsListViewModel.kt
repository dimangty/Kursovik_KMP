package com.example.kursovikkmp.feature.news.list

import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.feature.news.list.model.NewsList
import com.example.kursovikkmp.feature.news.list.model.NewsService
import info.javaway.spend_sense.extensions.appLog
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class NewsListViewModel(private val newsService: NewsService): BaseViewModel<NewsListState, NewsListEvents>() {
    override fun initialState() = NewsListState()

    fun loadNews() {

        viewModelScope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                //pushEvent(Event.Error(t.message.orEmpty()))
            }
        ) {
            val response = newsService.getNews()

            if (response.status.isSuccess()) {
                val regResponse = response.body<NewsList>()

                //pushEvent(Event.Success)
            } else {
                //val error = response.body<ApiErrorWrapper>().error
                //pushEvent(Event.Error(error?.message ?: response.bodyAsText()))
            }
        }

    }
}