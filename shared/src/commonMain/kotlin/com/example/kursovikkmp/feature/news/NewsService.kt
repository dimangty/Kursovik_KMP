package com.example.kursovikkmp.feature.news

import com.example.kursovikkmp.feature.news.model.Article
import com.example.kursovikkmp.network.NetworkSettings
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

interface NewsService {
    var news: MutableList<Article>
    suspend fun getNews(): HttpResponse
}

class NewsServiceImpl(
    private val client: HttpClient,
    private val settings: NetworkSettings
) : NewsService {

    override var news: MutableList<Article> = mutableListOf()

    override suspend fun getNews(): HttpResponse = client.get(settings.newsApiUrl)
}
