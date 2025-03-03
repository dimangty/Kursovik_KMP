package com.example.kursovikkmp.feature.news

import com.example.kursovikkmp.feature.news.model.Article
import com.example.kursovikkmp.network.NetworkSettings
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NewsService(private val client: HttpClient,
                  private val settings: NetworkSettings) {

    var news: MutableList<Article> = mutableListOf()

    suspend fun getNews() = client.get(settings.newsApiUrl)
}