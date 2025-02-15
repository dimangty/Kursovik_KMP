package com.example.kursovikkmp.feature.news.list.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsList(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article>
)