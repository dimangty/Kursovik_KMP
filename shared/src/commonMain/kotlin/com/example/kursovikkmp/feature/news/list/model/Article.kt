package com.example.kursovikkmp.feature.news.list.model

import com.example.kursovikkmp.database.ArticleDb
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String
)

fun ArticleDb.toEntity() = Article(author,
                                   title,
                                   description,
                                   url,
                                   urlToImage,
                                    publishedAt ?: "")



fun Article.toDb() = ArticleDb(author,
                               title,
                               description,
                               url,
                               urlToImage,
                               publishedAt)