package com.example.kursovikkmp.feature.news.model

import com.example.kursovikkmp.database.ArticleDb
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

fun ArticleDb.toEntity() = Article(author,
                                   title,
                                   description,
                                   url,
                                   urlToImage,
                                   publishedAt,
                                   content)



fun Article.toDb() = ArticleDb(author,
                               title,
                               description,
                               url,
                               urlToImage,
                               publishedAt,
                               content)

fun String?.toDateString() : String {
//2025-03-01T13:36:41Z
    if (this == null) return ""

    val date = this.split("T")[0]
    val components = date.split("-")
    val day = components[2].toInt()
    val month = components[1].toInt()
    val year = components[0].toInt()

    val dateTime = LocalDateTime(year, month, day, 10, 0, 0)
    val dayText = dateTime.dayOfMonth
    val monthText = dateTime.month.name
    return "$dayText $monthText"
}