package com.example.kursovikkmp.feature.news.list.model

import com.example.kursovikkmp.network.NetworkSettings
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NewsService(private val client: HttpClient,
                  private val settings: NetworkSettings) {

    suspend fun getNews() = client.get(settings.newsApiUrl)
}