package com.example.kursovikkmp.DB

import com.example.kursovikkmp.Database
import com.example.kursovikkmp.database.ArticleDb
import kotlin.coroutines.CoroutineContext
import com.example.kursovikkmp.feature.news.model.Article
import com.example.kursovikkmp.feature.news.model.toDb
import com.example.kursovikkmp.feature.news.model.toEntity

class ArticleDao(
    private val db: Database,
    private val coroutineContext: CoroutineContext
) {
    private val articleQueries = db.articleDbQueries

    fun getAll(): List<Article> =
        articleQueries
            .getAll()
            .executeAsList()
            .map{ it.toEntity()}

    suspend fun insert(article: Article) = articleQueries.insert(article.toDb())
    suspend fun delete(title: String) = articleQueries.delete(title = title)
    suspend fun check(title: String): Boolean = articleQueries.get(title = title).executeAsOneOrNull() != null
    suspend fun get(title: String): ArticleDb? = articleQueries.get(title = title).executeAsOneOrNull()
}