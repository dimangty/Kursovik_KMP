package com.example.kursovikkmp.feature.favorites.list

import com.example.kursovikkmp.DB.ArticleDao
import com.example.kursovikkmp.feature.news.list.model.Article
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val dao: ArticleDao
) {
    fun getAllFlow() = dao.getAll()
    suspend fun insert(article: Article)  = dao.insert(article)
    suspend fun delete(title: String)  = dao.delete(title)
    suspend fun check(title: String) = dao.check(title)
}